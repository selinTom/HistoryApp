package com.example.devov.historyapp.mvvm.weather;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.devov.historyapp.MainActivity;
import com.example.devov.historyapp.R;
import com.google.gson.Gson;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by devov on 2016/10/20.
 */

public class WeatherFragment extends Fragment  {
    private WeatherViewModel weatherViewModel;
    private WeatherLocalInfo weatherLocalInfo;
    private WeatherAdapter weatherAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ImageButton ib;
    private EditText et;
    private RecyclerView rv;
    private InputMethodManager imm;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager)(getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.weather_fragment,container,false);
        et=(EditText)v.findViewById(R.id.weather_et);
        ib=(ImageButton)v.findViewById(R.id.weather_ib);
        rv=(RecyclerView)v.findViewById(R.id.weather_rv);
        weatherAdapter=new WeatherAdapter(getContext());
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(weatherAdapter);
        bindViewModel();
      //  initData();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=et.getText().toString();
                weatherViewModel.getWeatherInfo(s);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    private void bindViewModel() {
        weatherViewModel=new WeatherViewModel();
        weatherViewModel.weatherObs
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                new Action1<WeatherInfo>() {
                    @Override
                    public void call(WeatherInfo s) {
                        if(s.getError_code()==0) {
                            Gson gson=new Gson();
                            String saveData=gson.toJson(s);
                        //    SQLiteDatabase sqLiteDatabase=((MainActivity)getActivity()).getDataBaseHelper().getWritableDatabase();
                            weatherLocalInfo = new WeatherLocalInfo();
                            weatherLocalInfo.setWeatherInfo(s);
                            weatherLocalInfo.setPhrase(et.getText().toString());
                            boolean flag=weatherAdapter.addData(weatherLocalInfo);
                            if(!flag)
                                Toast.makeText(getContext(), "该词语已经存在！", Toast.LENGTH_SHORT).show();
//                            else
//                                sqLiteDatabase.execSQL("insert into CachTable(parse) values("+saveData+")");
                        }
                        else
                            Toast.makeText(getActivity(),"该词语不存在！", Toast.LENGTH_SHORT).show();
                        et.setText("");

                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("aaa",throwable.toString());
                    }
                }
        );

    }
    private void initData(){
        SQLiteDatabase sqLiteDatabase=((MainActivity)getActivity()).getDataBaseHelper().getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from CachTable",null);
        cursor.moveToFirst();
        do{
      //      String s=
        }while (cursor.moveToNext());

    }
}
