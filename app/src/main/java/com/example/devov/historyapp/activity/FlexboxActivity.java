package com.example.devov.historyapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devov.historyapp.Model.NewsData;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.interfaces.Constant;
import com.example.devov.historyapp.utils.xUtilsHelper;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by devov on 2017/2/24.
 */

public class FlexboxActivity extends Activity implements Constant {
    @BindView(R.id.flexbox)
    FlexboxLayout flexboxLayout;
    private boolean netTag;
    List<NewsData.Result.Data>items=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox);
        ButterKnife.bind(this);
        initData();
        Log.i("aaa","Flex "+Thread.currentThread().getName());
    }
    private void initData() {
        if (xUtilsHelper.isNetworkConnected(this)) {
            netTag=true;
            RequestParams requestParams = new RequestParams(URL + "top");
            x.http().get(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            parserData(result);
                        }
                        @Override
                        public void onFinished() {}
                        @Override
                        public void onCancelled(CancelledException cex) {}
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {}
                    }
            );
        }
        else {
            if(netTag) {
                Toast.makeText(this, "无网络连接！", Toast.LENGTH_SHORT).show();
                netTag = false;
            }
        }
    }

    private void parserData(String result) {
        Gson gson=new Gson();
        int i=1;
        NewsData newsData=gson.fromJson(result,NewsData.class);
        items.addAll(newsData.getResult().getData());
        for(NewsData.Result.Data data:items ){
            TextView textView=new TextView(this);
            FlexboxLayout.LayoutParams layoutParams=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,xUtilsHelper.dip2px(this,20f),xUtilsHelper.dip2px(this,20));
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundResource(R.drawable.textview_background);
            textView.setText(i+++" "+data.getAuthor_name());
            textView.setTextSize(15);
            flexboxLayout.addView(textView);
        }
    }
}
