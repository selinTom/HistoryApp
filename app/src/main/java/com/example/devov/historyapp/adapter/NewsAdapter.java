package com.example.devov.historyapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.devov.historyapp.bean.NewsData;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.activity.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.devov.historyapp.utils.xUtilsHelper.displayImage;

/**
 * Created by devov on 2016/10/13.
 */

public class NewsAdapter extends BaseSwipeAdapter {
    private List<NewsData.Result.Data>datas;
    private LayoutInflater inflate;
    private Context context;
    private Fragment fragment;

    public NewsAdapter(Fragment fragment) {
        datas = new ArrayList<>();
        context=fragment.getActivity();
        inflate = LayoutInflater.from(this.context);
        this.fragment=fragment;
    }

    public void addData(List<NewsData.Result.Data> newData){
        datas.addAll(newData);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View view=inflate.inflate(R.layout.news_item,parent,false);
        ViewHolder viewHolder=new ViewHolder();
        viewHolder.iv=(ImageView)view.findViewById(R.id.iv_context);
        viewHolder.tv_context=(TextView)view.findViewById(R.id.tv_context);
        viewHolder.tv_date=(TextView)view.findViewById(R.id.tv_date);
        viewHolder.swipeLayout=(SwipeLayout)view.findViewById(R.id.swipe_layout);
        viewHolder.linearLayout=(LinearLayout)view.findViewById(R.id.delete);
        view.setTag(viewHolder);
       return view;
    }

    @Override
    public void fillValues(int position, View view) {
        ViewHolder viewHolder=(ViewHolder) view.getTag();
        if(datas!=null) {
            NewsData.Result.Data dataItem = datas.get(position);
            viewHolder.tv_context.setText(dataItem.getTitle());
            viewHolder.tv_date.setText(dataItem.getDate());
            displayImage(viewHolder.iv,dataItem.getThumbnail_pic_s());
            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            viewHolder.swipeLayout.getSurfaceView().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int type = fragment.getArguments().getInt("TYPE");
                            String url = datas.get(position).getUrl();
                            String picUrl = datas.get(position).getThumbnail_pic_s();
                            Intent intent = new Intent(context, DetailsActivity.class);
                            intent.putExtra("URL", url);
                            intent.putExtra("PIC_URL", picUrl);
                            intent.putExtra("TYPE", type);
                            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(fragment.getActivity(),
                                    Pair.create(view.findViewById(R.id.iv_context), "picShare"));
                            context.startActivity(intent, activityOptionsCompat.toBundle());
                        }
                    }
            );
            viewHolder.linearLayout.setOnClickListener(v->{
                viewHolder.swipeLayout.close();
                datas.remove(position);
                notifyDataSetChanged();
            });
        }

    }
//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder=null;
//        if(view==null){
//            viewHolder=new ViewHolder();
//            view=inflate.inflate(R.layout.news_item,viewGroup,false);
//            viewHolder.iv=(ImageView)view.findViewById(R.id.iv_context);
//            viewHolder.tv_context=(TextView)view.findViewById(R.id.tv_context);
//            viewHolder.tv_date=(TextView)view.findViewById(R.id.tv_date);
//            viewHolder.linearLayout=(LinearLayout)view.findViewById(R.id.delete);
//            viewHolder.swipeLayout=(SwipeLayout)view.findViewById(R.id.swipe_layout);
//            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Bottom,viewHolder.linearLayout);
//            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
//            Log.i("aaa","Position Original:"+position);
//            viewHolder.swipeLayout.getSurfaceView().setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            int type = fragment.getArguments().getInt("TYPE");
//                            String url = datas.get(position).getUrl();
//                            String picUrl = datas.get(position).getThumbnail_pic_s();
//                            Intent intent = new Intent(context, DetailsActivity.class);
//                            intent.putExtra("URL", url);
//                            intent.putExtra("PIC_URL", picUrl);
//                            intent.putExtra("TYPE", type);
//                            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(fragment.getActivity(),
//                                    Pair.create(v.findViewById(R.id.iv_context), "picShare"));
//                            context.startActivity(intent, activityOptionsCompat.toBundle());
//                        }
//                    }
//            );
//            viewHolder.linearLayout.setOnClickListener(v->{
//                Log.i("aaa","Position:"+position);
//                datas.remove(position);
//                notifyDataSetChanged();
//            });
//            view.setTag(viewHolder);
//        }
//        else {
//            viewHolder= (ViewHolder) view.getTag();
//        }
//        if(datas!=null) {
//            NewsData.Result.Data dataItem = datas.get(position);
//            viewHolder.tv_context.setText(dataItem.getTitle());
//            viewHolder.tv_date.setText(dataItem.getDate());
//            displayImage(viewHolder.iv,dataItem.getThumbnail_pic_s());
//
//
//        };
//        return view;
//    }
class ViewHolder{
    TextView tv_date;
    TextView tv_context;
    ImageView iv;
    SwipeLayout swipeLayout;
    LinearLayout linearLayout;
    }
}

