package com.myrecyclerviewadapter.vincent.lib;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vincent on 2017/1/20.
 */

public interface OnItemClickListener{
    /**
     * callback item click
     * @param parent
     * @param v
     * @param position
     */
    void onItemClick(ViewGroup parent, View v, int position);
}
