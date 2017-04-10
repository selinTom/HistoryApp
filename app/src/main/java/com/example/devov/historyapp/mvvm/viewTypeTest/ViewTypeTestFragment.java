package com.example.devov.historyapp.mvvm.viewTypeTest;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.devov.historyapp.R;
import com.example.devov.historyapp.mvvm.viewTypeTest.ip.IpAddressInfo;
import com.example.devov.historyapp.mvvm.viewTypeTest.ip.IpAddressViewModel;
import com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber.PhoneNumberInfo;
import com.example.devov.historyapp.mvvm.viewTypeTest.phoneNumber.PhoneNumberViewModel;
import com.example.devov.historyapp.utils.xUtilsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by devov on 2016/10/25.
 */

public class ViewTypeTestFragment extends Fragment {
    private IpAddressViewModel ipAddressViewModel;
    private PhoneNumberViewModel phoneNumberViewModel;
    private LinearLayoutManager linearLayoutManager;
    private ImageButton ib;
    private EditText et;
    private RecyclerView rv;
    private InputMethodManager imm;
    private ViewTypeTestAdapter viewTypeTestAdapter;
    private View fab;
    private boolean flag=false;
    List<BaseData> oldBaseDataArrayList;
    List<BaseData> newBaseDataArrayList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager)(getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.viewtype_fragment,container,false);
        et=(EditText)v.findViewById(R.id.viewtype_et);
        ib=(ImageButton)v.findViewById(R.id.viewtype_ib);
        rv=(RecyclerView)v.findViewById(R.id.viewtype_rv);
        fab=v.findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        viewTypeTestAdapter=new ViewTypeTestAdapter(getActivity());
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(viewTypeTestAdapter);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom=-xUtilsHelper.dip2px(getActivity(),10);

            }
        });
        rv.addOnScrollListener(onScrollListener);
        bindViewModel();
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String s = et.getText().toString();
                    if (Pattern.compile("[0-9]{11}").matcher(s).matches())
                        phoneNumberViewModel.getPhoneNumberInfo(Long.valueOf(s));
                    else {
                        Log.i("aaa", "ip 请求:" + s);
                        ipAddressViewModel.getIpAddressLocation(s);
                    }
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
        });
    }

    private void bindViewModel() {
        ipAddressViewModel=new IpAddressViewModel();
        phoneNumberViewModel=new PhoneNumberViewModel();
        ipAddressViewModel.ipAddressObs
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<IpAddressInfo>() {
                        @Override
                          public void call(IpAddressInfo ipAddressInfo) {
                            BaseData baseData=new BaseData();
                            String s=et.getText().toString();
                            baseData.setTag(BaseData.IP_DATA);
                            baseData.setInputData(s);
                            baseData.setIpAddressInfo(ipAddressInfo);
                            if(baseData.getIpAddressInfo().getError_code()==0) {
                                Log.i("aaa",ipAddressInfo.getResult().getLocation());
                                boolean isExist = viewTypeTestAdapter.addData(baseData);
                                if (!isExist)
                                    Toast.makeText(getContext(), "该数据已经存在！", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getContext(),"查询内容有误！",Toast.LENGTH_SHORT).show();
                            et.setText("");
                    }
                },

                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e("aaa","AQA"+throwable.toString());
                            }
                        });
        phoneNumberViewModel.phoneNumberObs
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<PhoneNumberInfo>() {
                            @Override
                            public void call(PhoneNumberInfo phoneNumberInfo) {
                                BaseData baseData=new BaseData();
                                String s=et.getText().toString();
                                baseData.setTag(BaseData.NUMBER_DATA);
                                baseData.setInputData(s);
                                baseData.setPhoneNumberInfo(phoneNumberInfo);
                                if(baseData.getPhoneNumberInfo().getError_code()==0) {
                                    Log.i("aaa",phoneNumberInfo.getResult().getCompany());
                                    boolean isExist = viewTypeTestAdapter.addData(baseData);
                                    Log.i("aaa",""+isExist);
                                    if (!isExist)
                                        Toast.makeText(getContext(), "该数据已经存在！", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(getContext(),"查询内容有误！",Toast.LENGTH_SHORT).show();
                                et.setText("");
                            }
                        },

                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e("aaa","QAQ"+throwable.toString());
                            }
                        });
        phoneNumberViewModel.excObs.subscribe(
                e->{
                    Log.i("aaa","ExcObs:"+e.toString());
                },
                exc->{
                    Log.i("aaa","onError:"+ exc.toString());
                }
        );
    }
    public View.OnClickListener listener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            oldBaseDataArrayList=viewTypeTestAdapter.getData();
            newBaseDataArrayList=new ArrayList<BaseData>();
            if(flag==false) {
                sort1();
                sort2();
                flag=true;
            }
            else{
                sort2();
                sort1();
                flag=false;
            }
            viewTypeTestAdapter.setData(newBaseDataArrayList);
            DiffUtil.DiffResult result=DiffUtil.calculateDiff(new DiffUtilCallback(newBaseDataArrayList,oldBaseDataArrayList));
            result.dispatchUpdatesTo(viewTypeTestAdapter);
        }

    };
    private void sort1(){
        for (int i = 0; i < oldBaseDataArrayList.size(); i++) {
            if (oldBaseDataArrayList.get(i).getTag() == BaseData.IP_DATA)
                newBaseDataArrayList.add(oldBaseDataArrayList.get(i));
        }
    }
    private void sort2(){
        for (int i = 0; i < oldBaseDataArrayList.size(); i++) {
            if (oldBaseDataArrayList.get(i).getTag() == BaseData.NUMBER_DATA)
                newBaseDataArrayList.add(oldBaseDataArrayList.get(i));
        }
    }
    private RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager=(LinearLayoutManager)rv.getLayoutManager();
            int firstPosition=linearLayoutManager.findFirstVisibleItemPosition();
            int lastPosition=linearLayoutManager.findLastVisibleItemPosition();
            int elevation=1;
            for(int i=firstPosition-1;i<=lastPosition+1;i++){
                View v=linearLayoutManager.findViewByPosition(i);
                if (v != null ) {
                    CardView cardView=(CardView) v.findViewById(R.id.card);
                    cardView.setCardElevation(xUtilsHelper.dip2px(getActivity(),elevation));
                    elevation+=5;
                    float translationY= cardView.getTranslationY();
                    if(i>firstPosition && translationY!=0)
                        cardView.setTranslationY(0);
                }
            }
            View firstView=linearLayoutManager.findViewByPosition(firstPosition);
            float firstViewTop=firstView.getTop();
            firstView.setTranslationY(-firstViewTop/2);

        }
    };
}
