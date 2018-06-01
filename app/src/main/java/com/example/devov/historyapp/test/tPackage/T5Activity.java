package com.example.devov.historyapp.test.tPackage;

import android.Manifest;
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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devov.historyapp.BaseApplication;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.utils.xUtilsHelper;
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

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by devov on 2017/5/10.
 */

public class T5Activity extends Activity {
    //2464354881
//    public static final String PIC_URL="http://img.wanyx.com/upload/picture/image/20131211/20131211110356_90032.jpg";
    public static final String PIC_URL="http://img.hhz1.cn/App-imageShow/o_phbig/e7f/f754e20p00go00000opuepp?iv=1&fmt=webp";
    @BindView(R.id.imageView)
    SimpleDraweeView imageView;
    @BindView(R.id.location_view)
    TextView locationView;
//    PublishSubject<String>locationObs;
    Observable<String>locationObs;
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
        List<String>providerList=locationManager.getProviders(true);
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
