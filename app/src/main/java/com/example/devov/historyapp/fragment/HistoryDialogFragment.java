package com.example.devov.historyapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devov.historyapp.Model.HistoryData;
import com.example.devov.historyapp.R;

import static com.example.devov.historyapp.utils.xUtilsHelper.displayImage;

/**
 * Created by devov on 2016/10/14.
 */

public class HistoryDialogFragment extends DialogFragment {
    private int year;
    private String title;
    private String des;
    private String lunar;
    private String pic;
    private HistoryData.Result result;
    private TextView titleTextView;
    private TextView desTextView;
    private TextView lunarTextView;
    private TextView yearTextView;
    private ImageView picImageView;
    public static HistoryDialogFragment newInstance(HistoryData.Result result){
        HistoryDialogFragment historyDialogFragment=new HistoryDialogFragment();
        Bundle args=new Bundle();
        args.putSerializable("RESULT",result);
        historyDialogFragment.setArguments(args);
        return historyDialogFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        getDialog().getWindow().setLayout(displayMetrics.widthPixels,(int)(displayMetrics.heightPixels*0.8));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        result=(HistoryData.Result) getArguments().getSerializable("RESULT");
        year=result.getYear();
        title=result.getTitle();
        des=result.getDes();
        lunar=result.getLunar();
        pic=result.getPic();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v=inflater.inflate(R.layout.history_dialog,container,false);
        titleTextView=(TextView)v.findViewById(R.id.dialog_title);
        yearTextView=(TextView)v.findViewById(R.id.dialog_year);
        lunarTextView=(TextView)v.findViewById(R.id.dialog_lunar);
        desTextView=(TextView)v.findViewById(R.id.dialog_des);
        picImageView=(ImageView)v.findViewById(R.id.dialog_pic);
        titleTextView.setText(title);
        yearTextView.setText(String.valueOf(year)+"年");
        desTextView.setText("  "+des);
        lunarTextView.setText(lunar);
        displayImage(picImageView,pic);
        return v;
    }
}
