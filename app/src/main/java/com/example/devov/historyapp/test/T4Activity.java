package com.example.devov.historyapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.ScrollerCompat;
import android.util.Log;
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
    ScrollerCompat scrollerCompat;
    boolean tag;
    int count=0;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t3);
        ButterKnife.bind(this);
        Log.i("aaa","main thread:"+Thread.currentThread().getName());
        TClass.test();
        Log.i("aaa","aaaaaaa");
        InjectClickEventDisposer.injectView(this);
        scrollerCompat=ScrollerCompat.create(this);

    }
    @InjectClickEvent(R.id.btn1)
    public View.OnClickListener listener1=v->{
//       ll.startMove(300);
        Log.i("aaa","btn1 click");
        scrollerCompat.fling(0,200,0,8000,0,0,-10000,10000);
    };
    @InjectClickEvent(R.id.btn2)
    public View.OnClickListener listener2=v->{
//        ll.startMove(-300);
        tag=true;
        new Thread(()->{
            Log.i("aaa","new thread start");
            while(tag){
//                if(count==10000) {
                    if(scrollerCompat.computeScrollOffset()){
                        Log.i("aaa", "current value:" + scrollerCompat.getCurrY());

                    }
                    count=0;
//                }
//                count++;
            }
        }).start();
    };
    @InjectClickEvent(R.id.btn3)
    public View.OnClickListener listener3=v->{
//        ll.startMove(-300);
        tag=false;

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