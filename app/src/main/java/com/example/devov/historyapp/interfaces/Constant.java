package com.example.devov.historyapp.interfaces;

import com.example.devov.historyapp.R;

/**
 * Created by devov on 2016/10/13.
 */

public interface Constant {
     String IP_URL="http://apis.juhe.cn/";
     String IP_KEY="0965b2ca37132af81c0abf989931241e";
     String PHONE_NUMBER_KEY="c01e489c6ea653790d62d61378a91bf9";
     String VOICE_WORD_URL="http://japi.juhe.cn/";
     String VOICE_WORD_KEY="d7f4084ee3cbda7636ff4820fc94d62a";
     String ROBOT_URL="http://op.juhe.cn/";
     String ROBOT_KEY="6fb2fb22dc997d1f305a83245b28a683";
     String WEATRER_URL="http://v.juhe.cn/";
     String WEATHER_KEY="201e23605fb6c44b589a6f8947958cb1";
     String URL="http://v.juhe.cn/toutiao/index?key=d5dce183a59a63aa71d454697eca23e3&type=";
     String DEFAULT="top";
     String SHEHUI="shehui";
     String GUONEI="guonei";
     String GUOJI="guoji";
     String YULE="yule";
     String TIYU="tiyu";
     String JUNSHI="junshi";
     String KEJI="keji";
     String CAIJING="caijing";
     String SHISHANG="shishang";
     String[] TAG={DEFAULT,SHEHUI,GUONEI,GUOJI,YULE,TIYU,JUNSHI,KEJI,CAIJING,SHISHANG};
     String[] items={"热门","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
      int[] srcResource={R.drawable.hot,R.drawable.life,R.drawable.country,R.drawable.world,R.drawable.entertainment,
            R.drawable.sports,R.drawable.military,R.drawable.science,R.drawable.business,R.drawable.fashion};
     String HISTORY_URL="http://api.juheapi.com/japi/toh?key=cf320b14c18f1647513727d244f2d265&v=1.0&";
     String DEMO="QR为英文 “Quick Response” 的缩写，即快速反应。QR码比普通条码可储存更多资料，扫描时的要求很低，无须像普通条码扫描时需直线对准扫描器才能识别。" +
             "QR码可以存储名片信息等大容量的内容，包括WIFI ACCESS，文档，数字，网址等。其应用越来越广泛，领域包括电子商务、签到、防伪等等。QR的形式可以一反往常的黑" +
             "白色调以及单调的方框，制作出很多有趣生动的QR二维码。现在QR的应用主要集中在电子票务，很多人接触QR源于火车票、飞机票上的二维码。这也间接提高了效率和准确率。未来不久，QR会迎来一个爆发期。";
}
