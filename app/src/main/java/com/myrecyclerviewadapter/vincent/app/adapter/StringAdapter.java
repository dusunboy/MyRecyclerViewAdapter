package com.myrecyclerviewadapter.vincent.app.adapter;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myrecyclerviewadapter.vincent.app.R;
import com.myrecyclerviewadapter.vincent.lib.BaseRecyclerViewAdapter;
import com.myrecyclerviewadapter.vincent.lib.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/2/4.
 */
public class StringAdapter extends BaseRecyclerViewAdapter<String, BaseViewHolder> {

    public StringAdapter(int layoutResId, List<String> list) {
        super(layoutResId, list);
    }

    @Override
    protected void onBindView(final BaseViewHolder baseViewHolder, String s, final int position) {
        TextView tv = baseViewHolder.getView(R.id.tv);
        ImageView iv = baseViewHolder.getView(R.id.iv);
        tv.setText(s);
        if (onItemOtherClickListener != null) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemOtherClickListener.onItemOtherViewClick(baseViewHolder.itemView, v, position);
                }
            });
        }
        if (iv != null) {
            iv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (itemTouchHelper != null) {
                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                            itemTouchHelper.startDrag(baseViewHolder);
                        }
                    }
                    return false;
                }
            });
        }
    }

}
