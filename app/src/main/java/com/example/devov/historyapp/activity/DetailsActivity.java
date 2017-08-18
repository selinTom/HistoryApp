package com.example.devov.historyapp.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.interfaces.Constant;
import com.example.devov.historyapp.swipeback.SwipeBackActivity;
import com.example.devov.historyapp.utils.xUtilsHelper;
import com.example.devov.historyapp.view.MyNestedParentView;

import org.xutils.common.Callback;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.devov.historyapp.utils.xUtilsHelper.XLogE;
import static com.example.devov.historyapp.utils.xUtilsHelper.displayImage;

/**
 * Created by devov on 2016/10/13.
 */

public class DetailsActivity extends SwipeBackActivity implements Constant{
    public interface PicUrl{
        @GET("{url}")
        Observable<ResponseBody>getBitmapFromUrl(@Path("url")String name);
    }
    @BindView(R.id.webview)
     WebView mWebView;
    @BindView(R.id.iv_detail)
    ImageView imageView;
    @BindView(R.id.scroll_view)
    MyNestedParentView scrollView;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    private int currentHeight;
    private int maxHeight;
    private int minHeight;
    private boolean changeState=false;
    private ValueAnimator fadeAnimator;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    @TargetApi(21)
    private void init() {
        setContentView(R.layout.details_activity_new);
        ButterKnife.bind(this);
     /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(500);
            getWindow().setEnterTransition(explode);
        }*/
        fadeAnimator=ValueAnimator.ofFloat(0,1);
        String url=getIntent().getStringExtra("URL");
        String pic_url=getIntent().getStringExtra("PIC_URL");
        int i=getIntent().getIntExtra("TYPE",0);
//        if(!pic_url.endsWith("/"))
//            pic_url+="/";
        Log.i("aaa","After Deal:"+dealWithString(pic_url,0));
        Observable<ResponseBody>responseObs= xUtilsHelper.RetrofitJson(PicUrl.class,dealWithString(pic_url,1)).getBitmapFromUrl(dealWithString(pic_url,0));
        String finalPic_url = pic_url;
        responseObs.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    InputStream inputStream=responseBody.byteStream();
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Palette.Builder builder=Palette.from(bitmap);
                    builder.generate(palette -> {
                        Palette.Swatch swatch=palette.getVibrantSwatch();
                        if(swatch!=null)rl2.setBackgroundColor(swatch.getRgb());
                        else rl2.setBackgroundColor(0x000000);
                    });
                },
                        e->{
                            Log.i("aaa", finalPic_url);
                            XLogE((Exception) e);
                        });
//        scrollView.setTouchEventCallback(touchEventCallback);
        String type=i==-1?"二维码链接":items[i];
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        if(pic_url!=null)
            displayImage(imageView, pic_url, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable drawable) {
                    imageView.post(() -> {
                        int drawableWidth = drawable.getIntrinsicWidth();
                        int scale = imageView.getWidth() / drawableWidth;
                        if (scale < 1) scale = 1;
                        currentHeight = imageView.getHeight() * scale;
                        maxHeight = imageView.getHeight() * scale;
                        minHeight = xUtilsHelper.dip2px(DetailsActivity.this, 56);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) currentHeight);
                        imageView.setLayoutParams(layoutParams);
                        rl2.setLayoutParams(layoutParams);
                        fadeAnimator.setDuration((int)(maxHeight-minHeight));
                        scrollView.setHeight(maxHeight,minHeight);
                    });
                }
                @Override
                public void onError(Throwable throwable, boolean b) {}
                @Override
                public void onCancelled(CancelledException e) {}
                @Override
                public void onFinished() {}
            });
        mWebView.loadUrl(url);
    }
    private String dealWithString(String str,int type){
        String[] strs=str.split("/");
        int index=(strs[0]+strs[1]+strs[2]).length()+2;
        if(type==0)
        return str.substring(index);
        else
            return strs[0]+"//"+strs[2]+"/";
    }




}




