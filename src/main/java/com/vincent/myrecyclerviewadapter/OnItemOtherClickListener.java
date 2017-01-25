package com.vincent.myrecyclerviewadapter;

import android.view.View;

/**
 * Created by Administrator on 2017/1/20.
 */

public interface OnItemOtherClickListener {

    /**
     * 点击事件
     * @param itemView
     * @param position
     */
    void onItemClick(View itemView, int position);
}
