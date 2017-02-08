package com.myrecyclerviewadapter.vincent.app.adapter;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myrecyclerviewadapter.vincent.app.R;
import com.myrecyclerviewadapter.vincent.lib.BaseRecyclerViewAdapter;
import com.myrecyclerviewadapter.vincent.lib.BaseViewHolder;

import java.util.List;

/**
 * Created by Vincent on 2017/2/7.
 */
public class StaggeredGridAdapter extends BaseRecyclerViewAdapter<String, BaseViewHolder> {

    public StaggeredGridAdapter(int layoutResId, List<String> list) {
        super(layoutResId, list);
    }

    @Override
    protected void onBindView(final BaseViewHolder baseViewHolder, String s, final int position) {
        LinearLayout li = baseViewHolder.getView(R.id.li);
        TextView tv = baseViewHolder.getView(R.id.tv);
        ImageView iv = baseViewHolder.getView(R.id.iv);
        if (position % 2 == 0) {
            li.setBackgroundColor(getColor(android.R.color.darker_gray));
        } else {
            li.setBackgroundColor(getColor(android.R.color.white));
        }
        tv.setText(s);
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
        if (onItemOtherClickListener != null) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemOtherClickListener.OnItemOtherViewClick(baseViewHolder.itemView, v, position);
                }
            });
        }

    }
}
