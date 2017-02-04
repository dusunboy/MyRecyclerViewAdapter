package com.myrecyclerviewadapter.vincent.lib;

import android.view.View;

/**
 * Created by Vincent on 2017/1/20.
 */

public interface OnItemOtherViewClickListener {

    /**
     * callback item other view click
     * @param parent
     * @param v
     * @param position
     */
    void OnItemOtherViewClick(View parent, View v, int position);
}
