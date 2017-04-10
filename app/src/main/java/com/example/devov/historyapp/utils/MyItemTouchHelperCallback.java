package com.example.devov.historyapp.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


/**
 * Created by devov on 2017/3/3.
 */

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final ItemTouchHelperAdapter itemTouchHelperAdapter;
    public MyItemTouchHelperCallback(ItemTouchHelperAdapter itemTouchHelperAdapter){
        this.itemTouchHelperAdapter=itemTouchHelperAdapter;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        int swipeFlags=ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        itemTouchHelperAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}
