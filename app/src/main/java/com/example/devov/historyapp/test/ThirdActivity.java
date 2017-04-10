package com.example.devov.historyapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.devov.historyapp.InjectClickEvent.InjectClickEvent;
import com.example.devov.historyapp.InjectClickEvent.InjectClickEventDisposer;
import com.example.devov.historyapp.R;

/**
 * Created by devov on 2017/3/12.
 */

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        InjectClickEventDisposer.InjectView(this);
        A a=new B();
        a.a();
        B b=new B();
        b.a();
    }
    @InjectClickEvent(R.id.tv0)
     public View.OnClickListener listener0=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ThirdActivity.this,"======>0",Toast.LENGTH_LONG).show();
            Test t=Test.T1;
            t.sayHello();
            Test t2=Test.T2;
            t2.sayHello();
        }
    };
    @InjectClickEvent(R.id.tv1)
    public View.OnClickListener listener1=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ThirdActivity.this,"======>1",Toast.LENGTH_LONG).show();
        }
    };
    @InjectClickEvent(R.id.tv2)
    public View.OnClickListener listener2=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ThirdActivity.this,"======>2",Toast.LENGTH_LONG).show();
        }
    };
    @InjectClickEvent(R.id.tv3)
    public View.OnClickListener listener3=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ThirdActivity.this,"======>3",Toast.LENGTH_LONG).show();
        }
    };
    enum Test{
        T1("",0){
            public void sayHello(){
                Log.i("aaa","T1 say Hello");
            }
        },T2(0,0),T3(0,0);
        public void sayHello() {
            Log.i("aaa", "Hello");
        }
         Test(String str,int i){
        }
        Test(int i,int m){}
    }


}
    //静态方法无法被重写
    class A{
        public static void a(){
            Log.i("aaa","Class A:Method a()");
        }
    }
    class B extends A{
        public static void a(){
            Log.i("aaa","Class B:Method a()");

        }
}