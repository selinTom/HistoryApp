package com.example.devov.historyapp.fragment.audioRecord;

/**
 * Created by devov on 2016/12/14.
 */

public class AskAndAnswerInfo {
    public static final int ME=0;
    public static final int ROBOT=1;
    private int type;
    private String dialog;
    public AskAndAnswerInfo(int type,String dialog){
        this.type=type;
        this.dialog=dialog;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }
}
