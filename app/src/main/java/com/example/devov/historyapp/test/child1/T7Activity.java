package com.example.devov.historyapp.test.child1;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.view.WindowUtils;

/**
 * Created by devov on 2017/8/15.
 */

public class T7Activity extends AppCompatActivity {
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        WindowUtils.showPopupWindow(T7Activity.this);

                    }
                }, 1000 * 3);

            }
        });
    }
}

