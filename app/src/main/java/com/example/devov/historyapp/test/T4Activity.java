package com.example.devov.historyapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.devov.historyapp.InjectClickEvent.InjectClickEvent;
import com.example.devov.historyapp.InjectClickEvent.InjectClickEventDisposer;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.view.MyScrollViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by devov on 2017/3/23.
 */

public class T4Activity extends Activity {
    @BindView(R.id.ll)
    MyScrollViewGroup ll;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    String url="http://img.hhz1.cn/App-imageShow/o_nphone/2c1/6341620ku08d00000o2zzol?iv=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t3);
        ButterKnife.bind(this);
        InjectClickEventDisposer.injectView(this);
    }
    @InjectClickEvent(R.id.btn1)
    public View.OnClickListener listener1=v->{
       ll.startMove(300);
    };
    @InjectClickEvent(R.id.btn2)
    public View.OnClickListener listener2=v->{
        ll.startMove(-300);
    };
    private void test(){
        ArrayList<String>list1=new ArrayList<>();
        ArrayList list2=new ArrayList<String>();
        list2.add("aa");
        list2.add(1);
        list1.add("Aa");
//        list1.add(1);
        Test t=new Test(0,0);
        Test.num++;
        Test.returnStr();

    }




}