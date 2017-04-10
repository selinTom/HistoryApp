package com.example.devov.historyapp.mvvm.weather;

import java.io.Serializable;
import java.util.List;

/**
 * Created by devov on 2016/10/21.
 */

public class WeatherInfo implements Serializable{
    private String reason;
    private Result result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public class Result{
        private String bushou;
        private String head;
        private String chengyujs;
        private String from_;
        private String example;
        private String yufa;
        private String ciyujs;
        private String yinzhengjs;
        private List<String>tongyi;
        private List<String>fanyi;

        public String getBushou() {
            return bushou;
        }

        public void setBushou(String bushou) {
            this.bushou = bushou;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getChengyujs() {
            return chengyujs;
        }

        public void setChengyujs(String chengyujs) {
            this.chengyujs = chengyujs;
        }

        public String getFrom_() {
            return from_;
        }

        public void setFrom_(String from_) {
            this.from_ = from_;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }

        public String getYufa() {
            return yufa;
        }

        public void setYufa(String yufa) {
            this.yufa = yufa;
        }

        public String getCiyujs() {
            return ciyujs;
        }

        public void setCiyujs(String ciyujs) {
            this.ciyujs = ciyujs;
        }

        public String getYinzhengjs() {
            return yinzhengjs;
        }

        public void setYinzhengjs(String yinzhengjs) {
            this.yinzhengjs = yinzhengjs;
        }

        public List<String> getTongyi() {
            return tongyi;
        }

        public void setTongyi(List<String> tongyi) {
            this.tongyi = tongyi;
        }

        public List<String> getFanyi() {
            return fanyi;
        }

        public void setFanyi(List<String> fanyi) {
            this.fanyi = fanyi;
        }
    }
}
