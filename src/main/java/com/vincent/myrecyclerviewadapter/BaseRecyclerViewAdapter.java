package com.vincent.myrecyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Vincent on 2017/1/20.
 */
abstract class BaseRecyclerViewAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K>{

    private static final int HEADER_VIEW = 0;
    public static final int FOOTER_VIEW = 1;
    private static final int LIST_VIEW = 2;
    private List<View> headerViews;
    private List<View> footViews;
    private int layoutResId;
    protected List<T> list;
    protected Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    protected OnItemOtherClickListener onItemOtherClickListener;
    protected ItemTouchHelper itemTouchHelper;

    BaseRecyclerViewAdapter(int layoutResId, List<T> list) {
        this.layoutResId = layoutResId;
        this.list = list;
        headerViews = new ArrayList<>();
        footViews = new ArrayList<>();
    }

    @Override
    public K onCreateViewHolder(final ViewGroup parent, int viewType) {
        K k = null;
        this.context = parent.getContext();
        layoutInflater = LayoutInflater.from(context);
        int viewTypeMod = viewType % 10;
        switch (viewTypeMod) {
            case HEADER_VIEW:
                int position = (viewType - HEADER_VIEW) / 10;
                k = createBaseViewHolder(parent, headerViews.get(position));
                break;
            case LIST_VIEW:
                k = createBaseViewHolder(parent, layoutResId);
                break;
        }
        if (k != null && viewTypeMod == LIST_VIEW) {
            final int position = (viewType - LIST_VIEW) / 10 - getHeaderViewsCount();
            k.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(parent, v, position);
                    }
                }
            });
            k.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(parent, v, position);
                    }
                    return true;
                }
            });
        }
        return k;
    }

    @Override
    public void onBindViewHolder(K k, int position) {
        int viewType = k.getItemViewType();
        int viewTypeMod = viewType % 10;
        switch (viewTypeMod) {
            case LIST_VIEW:
                position = position - getHeaderViewsCount();
                onBindView(k, list.get(position), position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeaderViewsCount()) {
            return HEADER_VIEW + position * 10;
        } else {
            return LIST_VIEW + position * 10;
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + list.size() + footViews.size();
    }

    @SuppressWarnings("unchecked")
    private K createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return (K) new BaseViewHolder(parent, getItemView(parent, layoutResId));
    }

    @SuppressWarnings("unchecked")
    private K createBaseViewHolder(ViewGroup parent, View view) {
        return (K) new BaseViewHolder(parent, view);
    }

    private View getItemView(ViewGroup parent, int layoutResId) {
        return layoutInflater.inflate(layoutResId, parent, false);
    }

    protected abstract void onBindView(K k, T t, int position);

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    void setOnItemOtherClickListener(OnItemOtherClickListener onItemOtherClickListener) {
        this.onItemOtherClickListener = onItemOtherClickListener;
    }
    void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void add(T t) {
        list.add(t);
        notifyItemInserted(list.size());
    }

    public void add(int position, T t) {
        list.add(position, t);
        notifyItemInserted(position);
    }

    public void addAll(List<T> t) {
        list.addAll(t);
        notifyItemInserted(list.size());
    }

    public void addAll(int position, List<T> t) {
        list.addAll(position, t);
        notifyItemRangeInserted(position, t.size());
    }

    public void remove(T t) {
        int position = list.indexOf(t);
        if (list.remove(t)) {
            notifyItemRemoved(position);
        }
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll(List<T> t) {
        if (list.removeAll(t)) {
            notifyDataSetChanged();
        }
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public boolean addHeaderView(View view) {
        boolean isAdd = headerViews.add(view);
        notifyDataSetChanged();
        return isAdd;
    }

    public void addHeaderView(int index, View view) {
        headerViews.add(index, view);
        notifyDataSetChanged();
    }

    public boolean addAllHeaderView(List<View> views) {
        boolean isAdd = headerViews.addAll(views);
        notifyDataSetChanged();
        return isAdd;
    }

    public boolean addAllHeaderView(int index, List<View> views) {
        boolean isAdd = headerViews.addAll(index, views);
        notifyDataSetChanged();
        return isAdd;
    }

    public boolean removeHeaderView(View view) {
        boolean isRemove = headerViews.remove(view);
        notifyDataSetChanged();
        return isRemove;
    }

    public View removeHeaderView(int index) {
        View view = headerViews.remove(index);
        notifyDataSetChanged();
        return view;
    }

    public void removeAllHeaderView() {
        headerViews.clear();
        notifyDataSetChanged();
    }

    public boolean removeAllHeaderView(List<View> views) {
        boolean isRemove = headerViews.removeAll(views);
        notifyDataSetChanged();
        return isRemove;
    }

    public int getHeaderViewsCount() {
        return headerViews.size();
    }

}
