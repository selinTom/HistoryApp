package com.example.devov.historyapp.mvvm.viewTypeTest;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.List;

/**
 * Created by devov on 2016/10/28.
 */

public class DiffUtilCallback extends DiffUtil.Callback {
    private List<BaseData> newBaseDataList,oldBaseDataList;
    public DiffUtilCallback(List<BaseData>newBaseDataList,List<BaseData>oldBaseDataList){
        this.newBaseDataList=newBaseDataList;
        this.oldBaseDataList=oldBaseDataList;
    }
    @Override
    public int getOldListSize() {
        return oldBaseDataList==null?0:oldBaseDataList.size();
    }

    @Override
    public int getNewListSize() {
        return newBaseDataList==null?0:newBaseDataList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Log.i("aaa","old:"+oldItemPosition+"  new:"+newItemPosition);
        if(newBaseDataList.get(newItemPosition).getInputData().equals(oldBaseDataList.get(oldItemPosition).getInputData())) {
            Log.i("aaa","true");
            return true;
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Log.i("aaa","areContentsTheSame");
        return true;
    }
}
