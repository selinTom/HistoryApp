package com.example.devov.historyapp;


import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by devov on 2016/12/20.
 */

public class MyAccessibilityService extends AccessibilityService {
    private boolean isClick=false;
    private AccessibilityNodeInfo absInfo;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();

        String eventText = "";

        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "TYPE_VIEW_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "TYPE_VIEW_FOCUSED";
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventText = "TYPE_VIEW_LONG_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                eventText = "TYPE_VIEW_SELECTED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "TYPE_VIEW_TEXT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                eventText = "TYPE_WINDOW_STATE_CHANGED";
//                Log.i("aaa",event.getClassName().toString()+"  "+event.getClassName().toString().equals("com.tencent.mobileqq.activity.SplashActivity"));
                if(event.getClassName().toString().equals("com.tencent.mobileqq.activity.SplashActivity")) {
//                    AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
//                    Log.i("aaa",nodeInfo.getText().toString());
//                    if(nodeInfo!=null){
//                        List<AccessibilityNodeInfo>nodeInfos=nodeInfo.findAccessibilityNodeInfosByText("我的电脑");
//                        Log.i("aaa",""+nodeInfo.getChildCount());
//                        for(AccessibilityNodeInfo info:nodeInfos){
//                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                        }
//                    }
                }
                if(event.getClassName().toString().equals("com.dataline.activities.LiteActivity")) {
                    Log.i("aaa","LiteActivity");
                    pasteText(event);
                }
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
                handleNotification(event);
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                eventText = "TYPE_ANNOUNCEMENT";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                eventText = "TYPE_VIEW_HOVER_ENTER";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                eventText = "TYPE_VIEW_HOVER_EXIT";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "TYPE_VIEW_SCROLLED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
                AccessibilityNodeInfo info=event.getSource();
                findChild(info,3);
                if(absInfo!=null)
                    Log.i("aaa","abs: "+absInfo.getChildCount());
//                findChild(info,0);
                break;
        }
        Log.i("aaa",eventText);
    }

    @Override
    public void onInterrupt() {

    }
    private void pasteText(AccessibilityEvent event){
        AccessibilityNodeInfo nodeInfo = event.getSource();
        findChild(nodeInfo,3);
        findChild(nodeInfo,1);
        findChild(nodeInfo,2);
    }
    private void handleNotification(AccessibilityEvent event){
        List<CharSequence>texts=event.getText();
        for(CharSequence text:texts){
            String str=text.toString();
//            Log.i("aaa",str+"  "+str.contains("你的帐号在电脑登录"));
            if(str.contains("你的帐号在电脑登录")){
//                Log.i("aaa",""+(event.getParcelableData()==null)+event.getParcelableData().getClass().toString());
//                if(event.getParcelableData()!=null && event.getParcelableData() instanceof Notification){
//                    Notification notification=(Notification)event.getParcelableData();
//                    PendingIntent pendingIntent=notification.contentIntent;
//                    Log.i("aaa","pendingIntent");
//                    try {
//                        pendingIntent.send();
//                    } catch (PendingIntent.CanceledException e) {
//                        Log.i("aaa",e.toString());
//                        e.printStackTrace();
//                    }
//                }
                Log.i("aaa","/***/");
                Intent intent=new Intent();
                intent.setClassName("com.tencent.mobileqq","com.tencent.mobileqq.activity.SplashActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
    private void findChild(AccessibilityNodeInfo info,int type){
//        Log.i("aaa","count:"+info.getChildCount());
       try{
        if(info!=null) {
//            Log.i("aaa", info.getChildCount() + "");
            int size = info.getChildCount();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    findChild(info.getChild(i), type);
                }
            }
            switch (type) {
                case 0:
                    if (!isClick) {
                        if (info.getText() != null) {
                            String s = info.getText().toString();
                            if (s.contains("搜索")) {
//                       isClick = true;
                                Log.i("aaa", "~~~~~~~~~~~~~~~~~~`");
                                info.getParent().getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                       info.getParent().getChild(1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                return;
                            }
                        }
                    }
                    break;
                case 1:
//                    Log.i("aaa", info.getClassName().toString());
                    ClipboardManager clipboard = (ClipboardManager) BaseApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", "hello world");
                    clipboard.setPrimaryClip(clip);
                    try {
                        if (Class.forName(info.getClassName().toString()) == EditText.class) {
                            boolean ok0 = info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
//                               Log.i("aaa", "ok0:" + ok0);
                            boolean ok1 = info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
//                               Log.i("aaa", "ok1:" + ok1);
                        }
                        clipboard.setPrimaryClip(null);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    if (Class.forName(info.getClassName().toString()) == Button.class) {
                        String s = info.getText().toString();
                        if (s.contains("发送")) {
                            info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                    break;
                case 3:
                    if (Class.forName(info.getClassName().toString()) == AbsListView.class) {
                        absInfo=info;
                    }
                default:
                    break;
                }
            }
       }catch(ClassNotFoundException e){e.printStackTrace();}
        return;
    }

}
