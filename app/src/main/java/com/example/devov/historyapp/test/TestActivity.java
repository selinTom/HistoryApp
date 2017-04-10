package com.example.devov.historyapp.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.fragment.audioRecord.VoiceApi;
import com.example.devov.historyapp.utils.xUtilsHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by devov on 2017/2/21.
 */

public class TestActivity extends Activity {
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TestAdapter testAdapter;
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        testAdapter=new TestAdapter(this);
        recyclerView.setAdapter(testAdapter);
        recyclerView.setHasFixedSize(true);
        mWebView=new WebView(TestActivity.this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
             mWebView.loadUrl(url);
                return false;
            }
        });

            ((LinearLayout)findViewById(R.id.ll)).addView(mWebView);
        //        findViewById(R.id.tv).setOnClickListener(v->startActivityForResult(new Intent(TestActivity.this,T2Activity.class),-1));
        Map<String,String>map=new HashMap<>();
        map.put("World","Hello");
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://www.baidu.com")
                .client(okHttpClient)
                .build();
        retrofit.create(VoiceApi.class).heheda(map).enqueue(new Callback<ResponseBody>() {
//        xUtilsHelper.RetrofitJson(VoiceApi.class,"https://www.baidu.com").heheda(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("aaa","onResponse");

                try {
                    Log.i("aaa","Height:"+mWebView.getHeight());
                    mWebView.loadDataWithBaseURL(null,response.body().string(), "text/html",  "utf-8", null);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("aaa","IOException:"+xUtilsHelper.getErrorInfoFromException(e));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("aaa","onFailure: "+xUtilsHelper.getErrorInfoFromException((Exception) t));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView v=new TextView(this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        v.setText("I'm back !");
        v.setTextSize(30);
        ((ViewGroup)findViewById(R.id.tv).getParent()).addView(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("aaa","Destroy"+getClass().getName());
    }
}
