package com.example.devov.historyapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.devov.historyapp.MainActivity;
import com.example.devov.historyapp.R;
import com.example.devov.historyapp.interfaces.Constant;

/**
 * Created by devov on 2016/10/13.
 */

public class MainMenuFragment extends Fragment implements Constant{
    private ListView lv;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.news_fragment,container,false);
        lv=(ListView)v.findViewById(R.id.news_listview);
        myAdapter=new MyAdapter();
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsFragment fragment=new NewsFragment();
                Bundle args=new Bundle();
                args.putString("TAG",TAG[i]);
                args.putInt("TYPE",i);
                fragment.setArguments(args);
                ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.context_fragment,fragment).commit();
                ((MainActivity)getActivity()).closeMenu();
            }
        });
        return v;
    }
     class MyAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return items[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder=null;
            if(view==null){
                view =LayoutInflater.from(getActivity()).inflate(R.layout.menu_item,viewGroup,false);
                viewHolder=new ViewHolder();
                viewHolder.iv=(ImageView) view.findViewById(R.id.menu_iv);
                viewHolder.tv=(TextView) view.findViewById(R.id.menu_tv);
                viewHolder.iv.setImageResource(srcResource[i]);
                viewHolder.tv.setText(items[i]);
                view.setTag(viewHolder);
            }
            else {
                viewHolder=(ViewHolder)view.getTag();
                viewHolder.iv.setImageResource(srcResource[i]);
                viewHolder.tv.setText(items[i]);

            }
             return view;
        }
    }
    private class ViewHolder {
        private ImageView iv;
        private TextView tv;
    }
}
