package com.example.devov.historyapp.test.result;

import android.Manifest;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devov.historyapp.BaseApplication;
import com.example.devov.historyapp.InjectClickEvent.InjectClickEvent;
import com.example.devov.historyapp.InjectClickEvent.InjectClickEventDisposer;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.test.AbstractActivity;
import com.example.devov.historyapp.utils.CustomErrorAction;
import com.example.devov.historyapp.utils.RxScreenshotDetector;
import com.example.devov.historyapp.utils.xUtilsHelper;
import com.example.devov.historyapp.view.WindowUtils;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

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

    /**
     * Created by devov on 2017/2/21.
     */

    public static class T2Activity extends AbstractActivity {
        @BindView(R.id.iv)
        ImageView imageView;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.et)
        EditText et;
        @BindView(R.id.iv2)
        ImageView iv2;
        private boolean flag;
        public String str="LOL";
        float translation=0;
        AnimationSet animationSet;
        PublishSubject<Integer> publishSubject=PublishSubject.create();
    //    TranslateAnimation animation=new TranslateAnimation(0,200,0,0);
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_t2);
            ButterKnife.bind(this);
            InjectClickEventDisposer.injectView(this);
            imageView.setOnClickListener(listener);
    //        Document document= Jsoup.parse("http://mini.eastday.com/mobile/170308155932166.html");
            animationSet=new AnimationSet(true);
            imageView.post(()->{
                ScaleAnimation scaleAnimation=new ScaleAnimation(1.5f,1f,1.5f,1,imageView.getWidth()/2,imageView.getHeight()/2);
                AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
                animationSet.setDuration(400);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(alphaAnimation);
            });
            double count=0.08d;
            while(count-->0d){
                Log.i("aaa","-->"+count );
            }
            RxTextView.textChanges(et).subscribe(str->{
                String emoji=str.toString();
                String emoji0=new String(Character.toChars(0x1f601));
              if(emoji.contains(emoji0)){
                    Log.i("aaa","穐穐穐穐穐穐");

    //                emoji.substring(emoji.indexOf(emoji0),emoji0.length());
                    emoji=emoji.replace(emoji0,"FAFA");
                }
                tv.setText(emoji);
            });
    //       Log.i("aaa",String.valueOf("重地".hashCode()));
    //       Log.i("aaa",String.valueOf("通话".hashCode()));
    //       Log.i("aaa",String.valueOf("方面".hashCode()));
    //       Log.i("aaa",String.valueOf("树人".hashCode()));
            publishSubject.subscribe(new CustomErrorAction<Integer>(
                    i->{
                        Log.i("aaa","I is :"+i);
                        if(i>10)
                        throw new RuntimeException("throw a exception!");
        }, CustomErrorAction.recordError( e->{
                Log.i("aaa","Exception!");
            })),
                   exc->{
                       Log.i("aaa","error ************");
                   } );

            RxScreenshotDetector.start(this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(path->tv.setText(tv.getText()+"\nScreen shot"+path),
                            exc-> xUtilsHelper.XLogE((Exception) exc));

        }
        private View.OnClickListener listener=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!flag){
                    v.setBackground(getResources().getDrawable(R.drawable.icon_like_article_s));
                    v.startAnimation(animationSet);
                }
                else
                    v.setBackground(getResources().getDrawable(R.drawable.icon_like_article_n));
                flag=!flag;

                setEmojiToTextView();
                test();
            }
        };
        private void setEmojiToTextView(){
            int unicodeJoy =0x1f601;
    //        int unicodeJoy =0xe79c8b;
    //        for(;unicodeJoy<0x1f64f;unicodeJoy++){
                String emojiString = getEmojiStringByUnicode(unicodeJoy);
    //            tv.append(emojiString);
    //            Log.i("aaa",emojiString);
    //        }
            tv.append(emojiString);

        }

        private String getEmojiStringByUnicode(int unicode){
            return new String(Character.toChars(unicode));
        }
        private void  function(){
              class A{
                A(){
                    Log.i("aaa","Construction");
                };
                  public void print(){
                      Log.i("aaa","InnerClass's function");
                  }
            }
            A a=new A();
            a.print();
        }
        @InjectClickEvent(R.id.iv2)
        public View.OnClickListener listener1=(v)->{
    //        animation.setFillAfter(true);
    //        animation.setInterpolator(new LinearInterpolator());
    //        animation.setDuration(1000);
    //        iv2.startAnimation(animation);
            translation=0;
            ValueAnimator valueAnimator=ValueAnimator.ofInt(0,500);
            valueAnimator.setDuration(1000)
                         .setInterpolator(new LinearInterpolator());

            valueAnimator.setEvaluator(new TypeEvaluator() {
                @Override
                public Object evaluate(float fraction, Object startValue, Object endValue) {
                    translation=((Integer)endValue-(Integer)startValue)*fraction-translation;
                    Log.i("aaa",fraction+"   "+String.valueOf(translation));
                    View v=getWindow().getDecorView();
                    v.scrollBy(10,0);
    //                return ((Integer)endValue-(Integer)startValue)*fraction;
                    return 0;
                }
            });
            valueAnimator.start();
        };
        public void test(){
            for(int i=0;i<20;i++){
                publishSubject.onNext(i(i));
            }

        }
        private int i(int i){
            Log.i("aaa","onNext================================");
            return i;
        }
    }

    /**
     * Created by devov on 2017/5/10.
     */

    public static class T5Activity extends Activity {
        //2464354881
    //    public static final String PIC_URL="http://img.wanyx.com/upload/picture/image/20131211/20131211110356_90032.jpg";
        public static final String PIC_URL="http://img.hhz1.cn/App-imageShow/o_phbig/e7f/f754e20p00go00000opuepp?iv=1&fmt=webp";
        @BindView(R.id.imageView)
        SimpleDraweeView imageView;
        @BindView(R.id.location_view)
        TextView locationView;
    //    PublishSubject<String>locationObs;
        Observable<String> locationObs;
        Location location;
        private LocationManager locationManager;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_t5);
            ButterKnife.bind(this);
            initPicView();
            getLocationInfo();


        }
        private void initPicView(){

            Fresco.getImagePipeline().clearCaches();
            ImageRequest request= ImageRequestBuilder.newBuilderWithSource(Uri.parse(PIC_URL))
                    .setProgressiveRenderingEnabled(true)
                    .build();
            ControllerListener<ImageInfo> controllerListener=new BaseControllerListener<ImageInfo>(){
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    if(imageInfo==null)return;
                    int imageHeight=imageInfo.getHeight();
                    int imageWidth=imageInfo.getWidth();
                    double scale=(double) BaseApplication.width/(double) imageWidth;
                    Log.i("aaa","imageWidth:"+imageHeight+"    width:"+BaseApplication.width+"   Scale:"+scale);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(BaseApplication.width,(int)(imageHeight*scale)));
                    Log.i("aaa","height:"+imageView.getLayoutParams().height);
                }
            };
            Postprocessor postprocessor=new Postprocessor() {
                @Override
                public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
                    return null;
                }

                @Override
                public String getName() {
                    return null;
                }

                @Nullable
                @Override
                public CacheKey getPostprocessorCacheKey() {
                    return null;
                }
            };
            DraweeController controller=Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setControllerListener(controllerListener)
                    .setOldController(imageView.getController())
                    .build();
            imageView.setHierarchy(new GenericDraweeHierarchyBuilder(imageView.getResources()).setFadeDuration(600).build());
            imageView.setController(controller);
    //        imageView.setImageURI(PIC_URL);
        }
        private void getLocationInfo(){
    //       getPermission();
            String provider;
            LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
    //        Criteria criteria=new Criteria();
    //        criteria.setAccuracy(Criteria.ACCURACY_FINE);
    //        criteria.setAltitudeRequired(false);
    //        criteria.setBearingRequired(false);
    //        criteria.setCostAllowed(false);
    //        criteria.setPowerRequirement(Criteria.POWER_LOW);
    //        String provider=locationManager.getBestProvider(criteria, true);
            List<String> providerList=locationManager.getProviders(true);
            for(String providerInfo:providerList)
                Log.i("aaa","Info:"+providerInfo);
            if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
                provider=LocationManager.NETWORK_PROVIDER;
    //            Toast.makeText(this, "Choose NETWORK"+provider, Toast.LENGTH_LONG).show();
            }
            else if(providerList.contains(LocationManager.GPS_PROVIDER)){
                provider=LocationManager.GPS_PROVIDER;
    //            Toast.makeText(this, "Choose GPS"+provider, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "No location provider to use", Toast.LENGTH_LONG).show();
                return;
            }
            Location location=locationManager.getLastKnownLocation(provider);
            if(location!=null){
                Log.i("aaa","location is null");
                locationView.setText("wait.....");
    //			showLocation(location);
            }
    //        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        @TargetApi(23)
        private void getPermission(){
            int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int flag0 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (flag!= PackageManager.PERMISSION_GRANTED || flag0!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        LocationListener locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(T5Activity.this, "onLocationChanged", Toast.LENGTH_LONG).show();

                Log.i("aaa","onLocationChanged");
                locationObs=Observable.just("WHERE")
                        .map(msg->{
                            Log.i("aaa","MSG:"+msg);
                            if(location==null)return "null";
                            Geocoder geocoder=new Geocoder(T5Activity.this);
                            try {
                                List<Address>address=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                if(address.size()<1)return "no address";
                                msg+="国家："+address.get(0).getCountryName()+"\n";
                                msg+="省："+address.get(0).getAdminArea()+"\n";
                                msg+="城市："+address.get(0).getLocality()+"\n";
                                msg+="名称："+address.get(0).getAddressLine(1)+"\n";
                                msg+="街道："+address.get(0).getAddressLine(0);
                            } catch (IOException e) {
                                xUtilsHelper.XLogE(e);
                                return "IOException";
                            }
                            return msg;})
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
                locationObs.subscribe(msg->locationView.setText(msg),
                        exc->xUtilsHelper.XLogE((Exception) exc));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("aaa","onStatusChanged");

            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("aaa","onProviderEnabled");

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("aaa","onProviderDisabled");

            }
        };
           @Override
        protected void onDestroy() {
            super.onDestroy();
            if(locationManager!=null)
                locationManager.removeUpdates(locationListener);
        }
    }

    /**
     * Created by devov on 2017/8/15.
     */

    public static class T7Activity extends AppCompatActivity {
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
}

