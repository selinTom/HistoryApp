package com.example.devov.historyapp.activity;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.devov.historyapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by devov on 2017/2/20.
 */

public class JSActivity extends Activity {
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.web_view)
    WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        ButterKnife.bind(this);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Log.i("aaa","JS onCreate");
//        webView.loadUrl("file:///android_asset/test.html");
//        webView.addJavascriptInterface(new JSBridge(), "android");
         webView.loadUrl("http://www.12306.cn");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("aaa","URL:"+url);
                if(url.contains("bilibili.com"))
                    webView.loadUrl("http://www.baidu.com");
//                webView.loadUrl("http://www.bilibili.com");
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
        btn.setOnClickListener(v->webView.loadUrl("javascript:modifyText(\"java调用js方法\")"));
    }
    public class JSBridge {
        @JavascriptInterface
        public void toastMessage(String message) {
            Log.i("aaa", "JSJSJSJSJS");
            Toast.makeText(JSActivity.this, getClass().getName()+" 通过Natvie传递的Toast:" + message, Toast.LENGTH_LONG).show();
        }
    }
}
