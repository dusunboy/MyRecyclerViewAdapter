package com.myrecyclerviewadapter.vincent.lib;

import android.view.View;

/**
 * Created by Vincent on 2017/1/20.
 */

public interface OnItemOtherViewClickListener {

    /**
     * Callback item other view click
     * @param parent
     * @param v
     * @param position
     */
    void onItemOtherViewClick(View parent, View v, int position);
}
