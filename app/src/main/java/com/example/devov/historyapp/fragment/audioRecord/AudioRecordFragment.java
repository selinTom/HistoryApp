package com.example.devov.historyapp.fragment.audioRecord;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.utils.AudioRecordUtil;
import com.example.devov.historyapp.view.RippleView;

import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

import static android.widget.Toast.makeText;

/**
 * Created by devov on 2016/12/14.
 */

public class AudioRecordFragment extends Fragment {
    private ImageButton imageButton;
    private FloatingActionButton btn;
    private FloatingActionButton retry;
    private ProgressBar pb;
    private AudioRecordUtil audioRecordUtil;
    private AudioRecordViewModel audioRecordViewModel;
    private LinearLayoutManager linearLayoutManager;
    private AudioRecordAdapter audioRecordAdapter;
    private RecyclerView recyclerView;
    private RippleView rippleView;
    private ImageView animationView;
    private Vibrator vibrator;
    private PublishSubject<String>slideToEndObs=PublishSubject.create();
    private AnimationDrawable animationDrawable;
    private boolean isOk=false;
    private String temp;
    private boolean isAnimationStart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.audio_record_fragment,container,false);
        vibrator = (Vibrator)(getActivity().getSystemService(Context.VIBRATOR_SERVICE));
        audioRecordUtil=AudioRecordUtil.getInstance();
        btn=(FloatingActionButton) v.findViewById(R.id.btn);
        retry=(FloatingActionButton) v.findViewById(R.id.retry);
        animationView=(ImageView)v.findViewById(R.id.animation_iv);
        btn.setOnClickListener(vv->{
            scrollToEnd("onClick");
            if(!isAnimationStart){
//                animationView.setVisibility(View.VISIBLE);
//                animationDrawable.start();
                isAnimationStart=true;
            }
            else{
//                animationDrawable.stop();
//                animationView.setVisibility(View.GONE);
                isAnimationStart=false;
            }
        });
        retry.setOnClickListener(vv->audioRecordViewModel.getAnswerFromRobot(temp));
        imageButton=(ImageButton)v.findViewById(R.id.iv_btn);
        imageButton.setOnTouchListener(onTouchListenerImpl);
        pb=(ProgressBar)v.findViewById(R.id.pb);
        rippleView=(RippleView)v.findViewById(R.id.ripple_view);
        recyclerView=(RecyclerView)v.findViewById(R.id.rv);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        audioRecordAdapter=new AudioRecordAdapter(getActivity());
        recyclerView.setAdapter(audioRecordAdapter);
//        animationDrawable=(AnimationDrawable)animationView.getBackground();
        getAudio();
        getWrite();
        bindViewModel();
        audioRecordUtil.setDisposeFailureListener(new AudioRecordUtil.DisposeFailureListener() {
            @Override
            public void disposeFailure() {
                AlertDialog alertDialog=new AlertDialog.Builder(getActivity())
                        .setTitle("出错啦")
                        .setMessage("录音器启动失败！")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                alertDialog.show();
                imageButton.setEnabled(false);
            }
        });
        btn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btn.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height=btn.getHeight();
                btn.setY(btn.getTop()+height/2);
                retry.setY(btn.getTop()+height/2);
            }
        }
        );

        return  v;
    }
    private View.OnTouchListener onTouchListenerImpl =new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    rippleView.startAnimation(imageButton.getHeight(),imageButton.getWidth());
                    vibrator.vibrate(150);
                    if(isOk){
                        audioRecordUtil.deleteFile();
                    }
                    audioRecordUtil.startRecord();
                    break;
                case MotionEvent.ACTION_UP:
                        audioRecordUtil.closeRecord();
                        String filename = audioRecordUtil.getFileName();
                        audioRecordViewModel.getVoiceWord(filename);
                        pb.setVisibility(View.VISIBLE);
                        imageButton.setVisibility(View.GONE);
                        rippleView.stop();
                    break;
            }
            return false;
        }
    };
    private void bindViewModel(){
        Toast toast= makeText(getActivity(),"好好说话",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        audioRecordViewModel=new AudioRecordViewModel();
        audioRecordViewModel.voiceWordObs
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data->{
                            String s=data.error_code;
                            if(!"0".equals(s)) {
                                toast.show();
                                imageButton.setVisibility(View.VISIBLE);
                                pb.setVisibility(View.GONE);
                            }
                            else{
                                AskAndAnswerInfo item=new AskAndAnswerInfo(AskAndAnswerInfo.ME,data.result);
                                Log.i("aaa","result:"+data.result);
                                temp=data.result;
                                audioRecordAdapter.addData(item);
                                audioRecordViewModel.getAnswerFromRobot(data.result);
                            }
                            ///////////////////////////////////////////////////////
//                            AskAndAnswerInfo item=new AskAndAnswerInfo(AskAndAnswerInfo.ME,"北京到通辽火车票");
//                            audioRecordAdapter.addData(item);
//                            audioRecordViewModel.getAnswerFromRobot("1");

////////////////////////////////////////////////////////////////////////////////////
//                            audioRecordUtil.deleteFile();
                            slideToEndObs.onNext("first");

                        }
                );
        audioRecordViewModel.answerInfoObs
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data->{
                            pb.setVisibility(View.GONE);
                            retry.setVisibility(View.GONE);
                            isOk=true;
                            AskAndAnswerInfo item;
                            if(!"0".equals(data.error_code)){
                                String s="你这话我没法接....";
                                item=new AskAndAnswerInfo(AskAndAnswerInfo.ROBOT,s);
                            }
                            else{
                                String text=data.result.text;
                                String url=data.result.url;
                                if(url!=null)
                                    text=text+":"+url;
                                item=new AskAndAnswerInfo(AskAndAnswerInfo.ROBOT,text);
                            }
                            audioRecordAdapter.addData(item);
//                            Log.i("aaa","size:"+audioRecordAdapter.getItemCount());
                            imageButton.setVisibility(View.VISIBLE);
                            slideToEndObs.onNext("second");
                        }
                );
        audioRecordViewModel.throwableObs
                .observeOn((AndroidSchedulers.mainThread()))
                .subscribe(
                        e-> {
                            retry.setVisibility(View.VISIBLE);
                            pb.setVisibility(View.GONE);
                            imageButton.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(),""+e.toString(),Toast.LENGTH_LONG).show();
                        }
                );
        slideToEndObs.observeOn(AndroidSchedulers.mainThread())
                .subscribe(str->scrollToEnd(str));
    }
    private synchronized void scrollToEnd(String tag){
//        int size=audioRecordAdapter.getItemCount()-1;
//        int lastPosition=linearLayoutManager.findLastVisibleItemPosition();
//        Log.i("aaa","last:"+lastPosition+"   size:"+size);
//        if(size>lastPosition){
//            linearLayoutManager.scrollToPositionWithOffset(audioRecordAdapter.getItemCount()-1,0);
//            linearLayoutManager.setStackFromEnd(true);
//        }
//        boolean isHasNew=recyclerView.canScrollVertically(1);
//        if(isHasNew){
//            linearLayoutManager.scrollToPositionWithOffset(audioRecordAdapter.getItemCount()-1,0);
//            linearLayoutManager.setStackFromEnd(true);
//        }
//        Log.i("aaa",tag+"   boolean"+isHasNew);
        boolean isSlideToBottom=audioRecordAdapter.isSlideToBottom(recyclerView);
        Log.i("aaa",tag+"  "+isSlideToBottom);
        if(!isSlideToBottom){
            linearLayoutManager.scrollToPositionWithOffset(audioRecordAdapter.getItemCount()-1,0);
            linearLayoutManager.setStackFromEnd(true);
        }
    }
    public void getAudio(){
        int flag = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
        if (flag!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},1);
        }
    }
    public void getWrite(){
        int flag = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (flag!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode!=1) {
            Toast.makeText(getActivity(),"你还没有给权限...",Toast.LENGTH_LONG).show();
            imageButton.setEnabled(false);
        }
        else
            imageButton.setEnabled(true);
    }
}
