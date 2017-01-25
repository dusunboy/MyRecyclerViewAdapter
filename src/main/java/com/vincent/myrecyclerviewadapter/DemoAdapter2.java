package com.vincent.myrecyclerviewadapter;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vincent on 2017-01-20 10:29:13.
 */
public class DemoAdapter2 extends BaseRecyclerViewAdapter<DemoBean, BaseViewHolder> {

    public DemoAdapter2(int layoutResId, List<DemoBean> list) {
        super(layoutResId, list);
    }

    @Override
    protected void onBindView(final BaseViewHolder baseViewHolder, DemoBean demoBean, final int position) {
        TextView tv = baseViewHolder.getView(R.id.tv);
        ImageView iv = baseViewHolder.getView(R.id.iv);
        tv.setText(demoBean.getStr());

//        iv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemOtherClickListener != null) {
//                    onItemOtherClickListener.onItemClick(v, position);
//                }
//            }
//        });
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

//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        //0a 1b 2c 3d 4e 5f 6g 7h 8i 9j 10k
//        if (fromPosition < toPosition) {
//            list.add(toPosition + 1, list.get(fromPosition));
//            list.remove(fromPosition);
//        } else {
//            DemoBean demoBean = list.remove(fromPosition);
//            list.add(toPosition, demoBean);
//        }
//        notifyItemMoved(fromPosition, toPosition);
//        String str = "";
//        for (int i = 0; i < list.size(); i++) {
//            str += list.get(i).getStr() + "-";
//        }
//        Log.i("onItemMove", str);
//        return true;
//    }
//
//    @Override
//    public void onItemDismiss(int position) {
//        notifyItemRemoved(position);
//    }
}
