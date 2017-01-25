package com.vincent.myrecyclerviewadapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/1/20.
 */

public interface OnItemClickListener {
    /**
     * 点击事件
     * @param parent
     * @param v
     * @param position
     */
    void onItemClick(ViewGroup parent, View v, int position);
}
