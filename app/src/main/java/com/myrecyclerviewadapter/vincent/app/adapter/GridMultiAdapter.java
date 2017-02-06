package com.myrecyclerviewadapter.vincent.app.adapter;

import android.widget.TextView;

import com.myrecyclerviewadapter.vincent.app.R;
import com.myrecyclerviewadapter.vincent.app.model.DemoBean;
import com.myrecyclerviewadapter.vincent.app.model.DemoImageBean;
import com.myrecyclerviewadapter.vincent.lib.BaseMultiRecyclerViewAdapter;
import com.myrecyclerviewadapter.vincent.lib.BaseViewHolder;

import java.util.List;

/**
 * Created by Vincent on 2017/2/6.
 */
public class GridMultiAdapter extends BaseMultiRecyclerViewAdapter<BaseViewHolder> {

    private static final int TEXT = 0;
    private static final int IMAGE = 1;

    public GridMultiAdapter(List<Object> list) {
        super(list);
    }

    @Override
    public int getItemViewType(int position) {
        if (get(position) instanceof DemoBean) {
            return TEXT;
        } else {
            return IMAGE;
        }
    }

    @Override
    protected int getLayoutResId(int viewType) {
        switch (viewType) {
            case TEXT:
                return R.layout.item_text;
            case IMAGE:
                return R.layout.item_text_image;
            default:
                return 0;
        }
    }


    @Override
    protected void onBindView(BaseViewHolder baseViewHolder, Object object, int position) {
        switch (baseViewHolder.getItemViewType()) {
            case TEXT:
                bindTextView(baseViewHolder, (DemoBean) object, position);
                break;
            case IMAGE:
                bindImageView(baseViewHolder, (DemoImageBean) object, position);
                break;
        }
    }

    private void bindImageView(BaseViewHolder baseViewHolder, DemoImageBean object, int position) {
        TextView tv = baseViewHolder.getView(R.id.tv);
        tv.setText(object.getString());
    }

    private void bindTextView(BaseViewHolder baseViewHolder, DemoBean object, int position) {
        TextView tv = baseViewHolder.getView(R.id.tv);
        tv.setText(object.getString());
    }

}
