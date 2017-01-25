package com.vincent.myrecyclerviewadapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/1/20.
 */

public interface OnItemLongClickListener {

    /**
     * 长按事件
     * @param parent
     * @param v
     * @param position
     */
    void onItemLongClick(ViewGroup parent, View v, int position);
}
