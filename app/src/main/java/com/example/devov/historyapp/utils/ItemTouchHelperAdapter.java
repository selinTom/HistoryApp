package com.example.devov.historyapp.utils;

/**
 * Created by devov on 2017/3/3.
 */

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition,int toPosition);
    void onItemDismiss(int position);
}
