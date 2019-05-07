package com.myrecyclerviewadapter.vincent.lib;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
/**
 * Created by Vincent on 2017/1/20.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private ViewGroup parent;

    public BaseViewHolder(ViewGroup parent, View itemView) {
        super(itemView);
        this.parent = parent;
        this.views = new SparseArray<View>();
    }

    /**
     * view cache
     * @param viewId
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * get view parent
     * @return
     */
    public ViewGroup getParent() {
        return parent;
    }

    /**
     * {@link ItemTouchHelper} <br/>
     * Let the view holder know that this item is being moved or dragged
     */
    public abstract void onBaseItemSelected();

    /**
     * {@link ItemTouchHelper} <br/>
     * Tell the view holder it's time to restore the idle state
     */
    public abstract void onBaseItemClear();
}
