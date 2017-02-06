package com.myrecyclerviewadapter.vincent.lib;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vincent on 2017/1/20.
 */

public interface OnItemLongClickListener {

    /**
     * Callback item long click
     * @param parent
     * @param v
     * @param position
     */
    void onItemLongClick(ViewGroup parent, View v, int position);
}
