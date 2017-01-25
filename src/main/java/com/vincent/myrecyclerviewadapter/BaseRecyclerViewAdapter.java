package com.vincent.myrecyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 *
 * Created by Vincent on 2017/1/20.
 */
abstract class BaseRecyclerViewAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K>{

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
    }

    @Override
    public K onCreateViewHolder(final ViewGroup parent, int viewType) {
        final K k;
        this.context = parent.getContext();
        layoutInflater = LayoutInflater.from(context);
        switch (viewType) {
            default:
                k = createBaseViewHolder(parent, layoutResId);
                break;
        }
        k.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, v, k.getAdapterPosition());
                }
            }
        });
        k.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(parent, v, k.getAdapterPosition());
                }
                return true;
            }
        });
        return k;
    }

    @Override
    public void onBindViewHolder(K k, int position) {
        int viewType = k.getItemViewType();
        switch (viewType) {
            default:
                onBindView(k, list.get(position), position);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressWarnings("unchecked")
    private K createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return (K) new BaseViewHolder(parent, getItemView(parent, layoutResId));
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
}
