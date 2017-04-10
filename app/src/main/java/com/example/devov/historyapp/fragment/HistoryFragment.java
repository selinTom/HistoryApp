package com.example.devov.historyapp.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devov.historyapp.Model.HistoryData;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.adapter.HistoryAdapter;
import com.example.devov.historyapp.interfaces.Constant;
import com.example.devov.historyapp.utils.MyItemTouchHelperCallback;
import com.example.devov.historyapp.utils.xUtilsHelper;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Calendar;

/**
 * Created by devov on 2016/10/14.
 */

public class HistoryFragment extends Fragment implements Constant {
    private LinearLayout linearLayout;
    private TextView tvMonth;
    private TextView tvDay;
    private int month;
    private int day;
    private int year;
    private boolean netTag = true;
    private Handler handler;
    private HistoryData historyData;
    private HistoryAdapter historyAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        Calendar myCalendar = Calendar.getInstance();
        month = myCalendar.get(Calendar.MONTH) + 1;
        year = myCalendar.get(Calendar.YEAR);
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        tvMonth = (TextView) view.findViewById(R.id.history_month);
        tvDay = (TextView) view.findViewById(R.id.history_date);
        linearLayout = (LinearLayout) view.findViewById(R.id.choose_date);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), myOnDateSetListener, year, month - 1, day);
                dpd.show();
            }
        });
        initDate();
        historyAdapter = new HistoryAdapter();
        initData();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        historyAdapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HistoryData.Result result = historyAdapter.getData().get(position);
                HistoryDialogFragment historyDialogFragment = HistoryDialogFragment.newInstance(result);
                historyDialogFragment.show(getFragmentManager(), "tag");
            }
        });
        recyclerView.setAdapter(historyAdapter);
        MyItemTouchHelperCallback callback=new MyItemTouchHelperCallback(historyAdapter);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    private void initDate() {
        tvMonth.setText(String.valueOf(month));
        tvDay.setText(String.valueOf(day));
    }

    private DatePickerDialog.OnDateSetListener myOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            month = i1 + 1;
            day = i2;
            initDate();
            initData();
        }
    };

    private void initData() {
        if (xUtilsHelper.isNetworkConnected(getActivity())) {
            netTag = true;
            RequestParams requestParams = new RequestParams(HISTORY_URL + getResources().getString(R.string.params, month, day));
            Log.i("History2", "month:" + (month) + " day:" + day);
            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            //加载成功回调，返回获取到的数据
                            Log.i("History", "onSuccess: " + result);
                            parserData(result);
                        }

                @Override
                        public void onFinished() {
                        }

                @Override
                        public void onCancelled(CancelledException cex) {
                        }

                @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                        }
                    }
            );
        } else {
            if (netTag) {
                Toast.makeText(getActivity(), "无网络连接！", Toast.LENGTH_SHORT).show();
                netTag = false;
            }
            handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initData();
                }
            }, 3000);

        }
    }

    private void parserData(String result) {
        Gson gson = new Gson();
        historyData = gson.fromJson(result, HistoryData.class);
        Log.i("History2", "complete" + historyData.getResult().get(3).getLunar());
        historyAdapter.addData(historyData.getResult());
        //     Log.i("History",newsData.getData().get(0).getDate()+"aaaaa");


    }
}