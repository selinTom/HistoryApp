package com.example.devov.historyapp.test.child3;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devov.historyapp.InjectClickEvent.InjectClickEvent;
import com.example.devov.historyapp.InjectClickEvent.InjectClickEventDisposer;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.test.AbstractActivity;
import com.example.devov.historyapp.utils.CustomErrorAction;
import com.example.devov.historyapp.utils.RxScreenshotDetector;
import com.example.devov.historyapp.utils.xUtilsHelper;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by devov on 2017/2/21.
 */

public class T2Activity extends AbstractActivity {
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
    PublishSubject<Integer>publishSubject=PublishSubject.create();
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
