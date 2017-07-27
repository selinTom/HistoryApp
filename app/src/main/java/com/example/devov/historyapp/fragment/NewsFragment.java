package com.example.devov.historyapp.fragment;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.example.devov.historyapp.Model.NewsData;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.adapter.NewsAdapter;
import com.example.devov.historyapp.interfaces.Constant;
import com.example.devov.historyapp.utils.dao.DaoManager;
import com.example.devov.historyapp.utils.dao.autoGenerate.News;
import com.example.devov.historyapp.utils.dao.autoGenerate.NewsDao;
import com.example.devov.historyapp.utils.xUtilsHelper;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by devov on 2016/10/13.
 */

public class NewsFragment extends Fragment implements Constant{
    private ListView lv;
    private NewsAdapter newsAdapter;
    private NewsData newsData;
    private Handler handler;
    private boolean netTag=true;
    private String param;
    private int titleBarHeight;
    NewsDao newsDao;
    public static NewsFragment getInstance(String tag,int type){
        NewsFragment newsFragment=new NewsFragment();
        Bundle args=new Bundle();
        args.putString("TAG",tag);
        args.putInt("TYPE",type);
        newsFragment.setArguments(args);
        return newsFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.news_fragment,container,false);
//        newsDao= DaoRepo.getInstance().getDaoSession().getNewsDao();
        newsDao= DaoManager.INSTANCE.getDaoSession().getNewsDao();
       lv=(ListView)view.findViewById(R.id.news_listview);
        newsAdapter=new NewsAdapter(this);
        lv.setAdapter(newsAdapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    int type = getArguments().getInt("TYPE");
//                    String url = newsData.getResult().getData().get(position).getUrl();
//                    String picUrl = newsData.getResult().getData().get(position).getThumbnail_pic_s();
//                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                    intent.putExtra("URL", url);
//                    intent.putExtra("PIC_URL", picUrl);
//                    intent.putExtra("TYPE", type);
//                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
//                            Pair.create(view.findViewById(R.id.iv_context), "picShare"));
//                    startActivity(intent, activityOptionsCompat.toBundle());
//            }
//        });
        param =getArguments().getString("TAG");
        view.setTag(param);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("aaa","onViewCreated    type:"+param);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("aaa","onActivityCreated    type:"+param);
        initData();
    }

    private void initData() {
        if (xUtilsHelper.isNetworkConnected(getActivity())) {
            netTag=true;
            RequestParams requestParams = new RequestParams(URL + param);
             x.http().get(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            //加载成功回调，返回获取到的数据
                            //            Log.i("History", "onSuccess: " + result);
                            parserData(result,true);

                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {


                        }
                    }
            );
        }
        else {
            if(netTag) {
                Toast.makeText(getActivity(), "无网络连接！", Toast.LENGTH_SHORT).show();
                netTag = false;
            }
            News locationNews=newsDao.queryBuilder().where(NewsDao.Properties.Type.eq(param)).unique();
            if(locationNews!=null)
                parserData(locationNews.getDocument(),false);
            handler=new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initData();
                }
            },3000);

        }
    }

    private void parserData(String result,boolean needClearData) {
        if(result!=null && !result.equals("")) {
            Gson gson = new Gson();
            newsData = gson.fromJson(result, NewsData.class);
            newsAdapter.addData(newsData.getResult().getData());
            News news = newsDao.queryBuilder().where(NewsDao.Properties.Type.eq(param)).build().unique();
            if (news != null && needClearData) {
                newsDao.delete(news);
                News insertNews = new News();
                insertNews.setType(param);
                insertNews.setDocument(result);
                newsDao.insert(insertNews);
            }
        }
   //     Log.i("History",newsData.getData().get(0).getDate()+"aaaaa");
//        try {
//            java.lang.reflect.Field field=newsData.getClass().getField("result");
//            NewsData.Result results=(NewsData.Result)field.get(newsData);
//            Log.i("aaa","result :"+results.getData().size()+results.getData().get(0).getUrl());
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("aaa",e.toString());
//        }
    }
    private void getTitleHeight(){
        Rect frame = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int contentTop = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        titleBarHeight = contentTop - statusBarHeight;
    }
    private Bitmap screenShot(){
        View view = getActivity().getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height = getActivity().getWindowManager().getDefaultDisplay().getHeight();//去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, -titleBarHeight, width, height +titleBarHeight);
        view.destroyDrawingCache();
        return b;
    }
}
