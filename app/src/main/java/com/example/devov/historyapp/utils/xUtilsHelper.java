package com.example.devov.historyapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.example.devov.historyapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devov on 2016/10/13.
 */

public class xUtilsHelper {
    private static String baseUrl;
    private static Retrofit retrofitFastJson;
    private static OkHttpClient OkHttpClient;
    private static ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setFailureDrawableId(R.drawable.failure)
            .setLoadingDrawableId(R.drawable.loading)
            .build();

    public static void displayImage(ImageView imageView, String iconUrl) {
        x.image().bind(imageView, iconUrl, imageOptions);
    }
    public static void displayImage(ImageView imageView, String iconUrl,Callback.CommonCallback<Drawable> commonCallback) {
        x.image().bind(imageView, iconUrl, imageOptions,commonCallback);
    }
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
    public static <T> T RetrofitJson(Class<T> clazz, String baseUrl) {
        if (OkHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new CheckInterceptor())
                    .addInterceptor(logging)
                    .build();
        }
        if (retrofitFastJson == null || xUtilsHelper.baseUrl != baseUrl) {
            xUtilsHelper.baseUrl = baseUrl;
            retrofitFastJson = new Retrofit.Builder()
                    .baseUrl(xUtilsHelper.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(OkHttpClient)
                    .build();
        }
        return retrofitFastJson.create(clazz);
    }

    public static List<String> getExtSDCardPath() {
        List<String> lResult = new ArrayList<String>();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard")) {
                    String[] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory()) {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        return lResult;
    }
    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }
    public static void XLogE(Exception e){
        Log.i("aaa",getErrorInfoFromException(e));
    }
    public static void encodeAsBitmap(String string, ImageView newQrCodeImageView) {
        try {
            String content=new String(string.getBytes("UTF-8"), "ISO-8859-1");
            Bitmap bitmap = null;
            BitMatrix bitMatrix = null;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 1500, 1500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap=barcodeEncoder.createBitmap(bitMatrix);
//            newQrCodeImageView.setTag(bitmap);
            newQrCodeImageView.setTag(qrCodeWithColor(bitMatrix));
            newQrCodeImageView.setImageBitmap(qrCodeWithColor(bitMatrix));
        } catch (WriterException we) {
            we.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            Log.i("aaa",e.toString());
            e.printStackTrace();
        }
    }

    public static String decodeFromBitmap(Bitmap bitmap) {
        int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(data, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), data);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            Log.i("aaa",e.toString());
         }
        return result.getText();
    }
    public static int getTitleHeight(Activity activity){
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - statusBarHeight;
    }
    private static Bitmap qrCodeWithColor(BitMatrix bitMatrix){
        int width=bitMatrix.getWidth();
        int height=bitMatrix.getHeight();
        int pixels[]=new int[width*height];
        for(int y=0;y<height;y++)
            for(int x=0;x<width;x++){
                if(bitMatrix.get(x,y))
                    pixels[y*width+x]=0xff0000ff;
                else
                    pixels[y*width+x]=0xffffffff;
            }
        Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels,0,width,0,0,width,height);
        return bitmap;
    }
    public static void startUCrop(Activity activity,String pathUri){
        File outDir= new File(Environment.getExternalStorageDirectory(),"ucrop");
        if(!outDir.exists())
            outDir.mkdir();
        UUID id=UUID.randomUUID();
        File uCropPic=new File(outDir,id+".jpg");
        Uri uCropUri=Uri.fromFile(uCropPic);
        Uri originalUri=Uri.parse(pathUri);
        UCrop uCrop=UCrop.of(originalUri,uCropUri);
        UCrop.Options options=new UCrop.Options();
        options.setAllowedGestures(UCropActivity.SCALE,UCropActivity.ROTATE,UCropActivity.ALL);
//        options.setHideBottomControls(true);
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.colorAccent));
        options.setFreeStyleCropEnabled(true);
        uCrop.withOptions(options);
        uCrop.withAspectRatio(16,9);
        uCrop.start(activity,UCrop.REQUEST_CROP);
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
     class CheckInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Log.i("aaa", "Request OK");
            Response response = chain.proceed(chain.request());
            Log.i("aaa", "Response OK");
            return response;
        }
    }
