package com.example.devov.historyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import com.example.devov.historyapp.activity.FlexboxActivity;
import com.example.devov.historyapp.activity.PhotoWallActivity;
import com.example.devov.historyapp.activity.QrActivity;
import com.example.devov.historyapp.adapter.SplashAdapter;
import com.example.devov.historyapp.test.T4Activity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SplashAdapter splashAdapter;
    private Button btn;
    private long firstTime;
    Timer timer;
    TimerTask timerTask;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_slash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        recyclerView=(RecyclerView)findViewById(R.id.grid);
        btn=(Button)findViewById(R.id.zxing_btn);
        btn.setText(Title.TITLE);
        btn.setOnClickListener(v->{
            Intent intent=new Intent(SplashActivity.this,QrActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.photo_wall_btn).setOnClickListener(v->{
            Intent i=new Intent(SplashActivity.this, PhotoWallActivity.class);
            startActivity(i);
        });
        findViewById(R.id.flexbox).setOnClickListener(v->startActivity(new Intent(SplashActivity.this, FlexboxActivity.class)));
//        findViewById(R.id.js_btn).setOnClickListener(v->startActivity(new Intent(SplashActivity.this, JSActivity.class)));
        findViewById(R.id.js_btn).setOnClickListener(v->startActivity(new Intent(SplashActivity.this, T4Activity.class)));
        splashAdapter=new SplashAdapter(this);
        setListener();
        gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(splashAdapter);
//        Log.i("aaa","Main:"+Thread.currentThread().toString());
//        btn.post(()->Log.i("aaa","Height:"+btn.getHeight()));
//
//        timer=new Timer();
//        timerTask=new TimerTask() {
//            @Override
//            public void run() {
//                i++;
//                Log.i("aaa","count :"+i);
//                Log.i("aaa","Timer :"+Thread.currentThread().toString());
//                if(i==5){
//                    findViewById(R.id.js_btn).post(()->((TextView)findViewById(R.id.js_btn)).setText("TIMER!!"));
//                }
//            }
//        };
//        new Thread(()-> {
//            timer.schedule(timerTask,0,3000);
//            Log.i("aaa","New:"+Thread.currentThread().toString());
//        },"NEW THREAD").start();

    }

    private void setListener() {
        SplashAdapter.OnClickListener listener=new SplashAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                MainActivity.LauncherActivity(position,SplashActivity.this);
//                findViewById(R.id.js_btn).setVisibility(View.GONE);
            }
        };
        splashAdapter.setOnClickListener(listener);
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
//            Toast.makeText(SplashActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.coordinatorLayout),"再按一次退出",Snackbar.LENGTH_LONG).show();
            firstTime = secondTime;
        } else {
            finish();
    }
//        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
//        launcherIntent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(launcherIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        timer.cancel();
        Log.i("aaa","Destroy"+getClass().getName());
    }
}