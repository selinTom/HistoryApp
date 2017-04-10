package com.example.devov.historyapp.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.utils.xUtilsHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by devov on 2017/1/19.
 */

public class QrActivity extends AppCompatActivity {
    @BindView(R.id.main_layout)
    View v;
    @BindView(R.id.new_qrcode)
    ImageView createQrCodeImageView;
    @BindView(R.id.scan)
    ImageView scanQrCodeImageView;
    @BindView(R.id.generate_code)
    ImageView newQrCodeImageView;
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.edit_qr_code)
    EditText editText;
    private boolean tag;
    private InputMethodManager imm;
    private Handler handler=new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        imm = (InputMethodManager)(getSystemService(Context.INPUT_METHOD_SERVICE));
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                textView.setY(v.getHeight());
            }
        });
        createQrCodeImageView.setOnClickListener(v-> {
            String str=editText.getText().toString();
            if(str!=null&&!("".equals(str))){
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                xUtilsHelper.encodeAsBitmap(str,newQrCodeImageView);
            }
            else {
                textView.setText("这啥都生成不出来OAO ");
                handler.postDelayed(() -> slideTextView(), 50);
                handler.postDelayed(() -> slideTextView(), 3050);
            }
        });
        createQrCodeImageView.setOnLongClickListener(v->{
            Bitmap bitmap=(Bitmap)(newQrCodeImageView.getTag());
            if(bitmap!=null){
                String str=xUtilsHelper.decodeFromBitmap(bitmap);
                if(!checkLink(str)) {
                    textView.setText(str);
                    handler.postDelayed(() -> slideTextView(), 50);
                }
            }
            return true;
        }
        );
        scanQrCodeImageView.setOnClickListener(v->{
            IntentIntegrator integrator=new IntentIntegrator(this);
            integrator.setCaptureActivity(ScanActivity.class);
            integrator.initiateScan();
        });
    }
    private void slideTextView(){
        int height=v.getHeight();
        ValueAnimator valueAnimator=null;
        if(!tag)
            valueAnimator=ValueAnimator.ofInt(height,height-textView.getHeight());
        else
            valueAnimator=ValueAnimator.ofInt(height-textView.getHeight(),height);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int y=(int)animation.getAnimatedValue();
                textView.setY((float)y);
            }
        });
        tag=!tag;
        valueAnimator.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult!=null){
            if(intentResult.getContents()==null){
                textView.setText("Nothing has been found");
                handler.postDelayed(()->slideTextView(),50);
                handler.postDelayed(()->slideTextView(),3050);
            }
            else{
                String str=intentResult.getContents();
                if(!checkLink(str)) {
                    textView.setText(str);
                    handler.postDelayed(() -> slideTextView(), 50);
                    handler.postDelayed(() -> slideTextView(), 3050);
                }
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }
    private boolean checkLink(String link){
        if(link!=null)
            if(link.length()>8)
                if(link.substring(0,7).equals("http://")||link.substring(0,8).equals("https://")){
                    Intent intent=new Intent(this,DetailsActivity.class);
                    intent.putExtra("TYPE",-1);
                    intent.putExtra("URL",link);
                    startActivity(intent);
                    return true;
                }
        return false;
    }
}
