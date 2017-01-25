package com.vincent.myrecyclerviewadapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/1/20.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private ViewGroup parent;

    public BaseViewHolder(ViewGroup parent, View itemView) {
        super(itemView);
        this.parent = parent;
        this.views = new SparseArray<View>();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public ViewGroup getParent() {
        return parent;
    }

}
