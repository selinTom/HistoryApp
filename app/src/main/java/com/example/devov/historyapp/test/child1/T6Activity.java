package com.example.devov.historyapp.test.child1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.test.Test1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devov on 2017/6/29.
 */

public class T6Activity extends Activity {
    interface Source<T> {
        T nextT();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test0);
        demo(()->{return new TextView(this);
        });
        List<Object>list=new ArrayList<>();
        List<String>list1=new ArrayList<>();
        list1.add("Aa");
        list1.add("Aa");
        list1.add("Aa");
        list1.add("Aa");
//        list1.addAll(list);
//        test(new Test1().func());
        Log.i("aaa","aaaaaaaaaaa");
        new Test1().tt();
    }
    void demo(Source<? extends View> strs) {
//        Source<Object> objects = strs; // ！！！在 Java 中不允许
//        Source<TextView> objects = strs; // ！！！在 Java 中不允许
// ……
    }
    void test(ArrayList<String> list){
        Log.i("aaa","ssssssssssssss");

    }


}
