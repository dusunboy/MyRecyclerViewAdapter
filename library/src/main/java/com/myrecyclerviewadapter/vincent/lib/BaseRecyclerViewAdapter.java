package com.myrecyclerviewadapter.vincent.lib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2017/1/20.
 */
public abstract class BaseRecyclerViewAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K>{

    private static final int HEADER_VIEW = 0;
    public static final int FOOTER_VIEW = 1;
    private static final int LIST_VIEW = 2;
    private List<View> headerViews;
    private List<View> footerViews;
    private int layoutResId;
    protected List<T> list;
    protected Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    protected OnItemOtherViewClickListener onItemOtherClickListener;
    protected ItemTouchHelper itemTouchHelper;

    public BaseRecyclerViewAdapter(int layoutResId, List<T> list) {
        this.layoutResId = layoutResId;
        this.list = list;
        headerViews = new ArrayList<>();
        footerViews = new ArrayList<>();
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
            case FOOTER_VIEW:
                position = (viewType - FOOTER_VIEW) / 10 - list.size();
                k = createBaseViewHolder(parent, footerViews.get(position));
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
        } else if (position >= (list.size() + getHeaderViewsCount())) {
            return FOOTER_VIEW + position * 10;
        } else {
            return LIST_VIEW + position * 10;
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + list.size() + getFooterViewsCount();
    }

    /**
     * create BaseViewHolder
     * @param parent
     * @param layoutResId
     * @return
     */
    @SuppressWarnings("unchecked")
    private K createBaseViewHolder(ViewGroup parent, int layoutResId) {
        return (K) new BaseViewHolder(parent, getItemView(parent, layoutResId)) {
            @Override
            public void onBaseItemSelected() {
                onItemSelected();
            }

            @Override
            public void onBaseItemClear() {
                onItemClear();
            }
        };
    }

    /**
     * Create BaseViewHolder
     * @param parent
     * @param view
     * @return
     */
    @SuppressWarnings("unchecked")
    private K createBaseViewHolder(ViewGroup parent, View view) {
        return (K) new BaseViewHolder(parent, view) {
            @Override
            public void onBaseItemSelected() {
                onItemSelected();
            }

            @Override
            public void onBaseItemClear() {
                onItemClear();
            }
        };
    }

    /**
     * {@link android.support.v7.widget.helper.ItemTouchHelper} <br/>
     * Let the view holder know that this item is being moved or dragged
     */
    public void onItemSelected() {

    };

    /**
     * {@link android.support.v7.widget.helper.ItemTouchHelper} <br/>
     * Tell the view holder it's time to restore the idle state
     */
    public void onItemClear() {

    };

    /**
     * Returns a new view hierarchy from the specified xml resource
     * @param parent
     * @param layoutResId
     * @return
     */
    private View getItemView(ViewGroup parent, int layoutResId) {
        return layoutInflater.inflate(layoutResId, parent, false);
    }

    /**
     * Bind each view
     * @param k
     * @param t
     * @param position
     */
    protected abstract void onBindView(K k, T t, int position);

    /**
     * Register a callback to be invoked when this view is clicked.
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has been clicked.
     * @param onItemOtherClickListener
     */
    public void setOnItemOtherClickListener(OnItemOtherViewClickListener onItemOtherClickListener) {
        this.onItemOtherClickListener = onItemOtherClickListener;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has been clicked and held
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * Returns List
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Returns the number of elements in this list.
     * @return
     */
    public int listSize() {
        return list.size();
    }

    /**
     * Returns the element at the specified position in this list.
     * @param position
     * @return
     */
    public T get(int position) {
        return list.get(position);
    }

    /**
     * Lists that support this operation may place limitations on what elements may be added to this list. In particular, some lists will refuse to add null elements, and others will impose restrictions on the type of elements that may be added. List classes should clearly specify in their documentation any restrictions on what elements may be added.
     * @param t
     */
    public void add(T t) {
        list.add(t);
        notifyItemInserted(list.size());
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation). Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * @param position
     * @param t
     */
    public void add(int position, T t) {
        list.add(position, t);
        notifyItemInserted(position);
    }

    /**
     * Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's iterator (optional operation). The behavior of this operation is undefined if the specified collection is modified while the operation is in progress. (Note that this will occur if the specified collection is this list, and it's nonempty.)
     * @param t
     */
    public void addAll(List<T> t) {
        list.addAll(t);
        notifyItemInserted(list.size());
    }

    /**
     * Inserts all of the elements in the specified collection into this list at the specified position (optional operation). Shifts the element currently at that position (if any) and any subsequent elements to the right (increases their indices). The new elements will appear in this list in the order that they are returned by the specified collection's iterator. The behavior of this operation is undefined if the specified collection is modified while the operation is in progress. (Note that this will occur if the specified collection is this list, and it's nonempty.)
     * @param position
     * @param t
     */
    public void addAll(int position, List<T> t) {
        list.addAll(position, t);
        notifyItemRangeInserted(position, t.size());
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present (optional operation). If this list does not contain the element, it is unchanged. More formally, removes the element with the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))) (if such an element exists). Returns true if this list contained the specified element (or equivalently, if this list changed as a result of the call).
     * @param t
     */
    public void remove(T t) {
        int position = list.indexOf(t);
        if (list.remove(t)) {
            notifyItemRemoved(position);
        }
    }

    /**
     * Removes the element at the specified position in this list (optional operation). Shifts any subsequent elements to the left (subtracts one from their indices). Returns the element that was removed from the list.
     * @param position
     */
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Removes from this list all of its elements that are contained in the specified collection (optional operation).
     * @param t
     */
    public void removeAll(List<T> t) {
        if (list.removeAll(t)) {
            notifyDataSetChanged();
        }
    }

    /**
     * set {@link ItemTouchHelper}
     * @param itemTouchHelper
     */
    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    /**
     * Add view to the end of this header view
     * @param view
     * @return
     */
    public boolean addHeaderView(View view) {
        boolean isAdd = headerViews.add(view);
        notifyDataSetChanged();
        return isAdd;
    }

    /**
     * Inserts the specified view at the specified position in this header view
     * @param index
     * @param view
     */
    public void addHeaderView(int index, View view) {
        headerViews.add(index, view);
        notifyDataSetChanged();
    }

    /**
     * Add all of the views to the end of this header view
     * @param views
     * @return
     */
    public boolean addAllHeaderView(List<View> views) {
        boolean isAdd = headerViews.addAll(views);
        notifyDataSetChanged();
        return isAdd;
    }

    /**
     * Inserts all of the views at the specified position in this header view
     * @param index
     * @param views
     * @return
     */
    public boolean addAllHeaderView(int index, List<View> views) {
        boolean isAdd = headerViews.addAll(index, views);
        notifyDataSetChanged();
        return isAdd;
    }

    /**
     * Removes the specified view from this header view
     * @param view
     * @return
     */
    public boolean removeHeaderView(View view) {
        boolean isRemove = headerViews.remove(view);
        notifyDataSetChanged();
        return isRemove;
    }

    /**
     * Removes the specified view at the specified position in this header view
     * @param index
     * @return
     */
    public View removeHeaderView(int index) {
        View view = headerViews.remove(index);
        notifyDataSetChanged();
        return view;
    }

    /**
     * Removes all of the views from this header view
     */
    public void removeAllHeaderView() {
        headerViews.clear();
        notifyDataSetChanged();
    }

    /**
     * Removes from this header view all of its views that are contained in the specified collection
     * @param views
     * @return
     */
    public boolean removeAllHeaderView(List<View> views) {
        boolean isRemove = headerViews.removeAll(views);
        notifyDataSetChanged();
        return isRemove;
    }

    /**
     * Returns the total number of header views by the adapter.
     * @return
     */
    public int getHeaderViewsCount() {
        return headerViews.size();
    }


    /**
     * Add view to the end of this footer view
     * @param view
     * @return
     */
    public boolean addFooterView(View view) {
        boolean isAdd = footerViews.add(view);
        notifyDataSetChanged();
        return isAdd;
    }

    /**
     * Inserts the specified view at the specified position in this footer view
     * @param index
     * @param view
     */
    public void addFooterView(int index, View view) {
        footerViews.add(index, view);
        notifyDataSetChanged();
    }

    /**
     * Add all of the views to the end of this footer view
     * @param views
     * @return
     */
    public boolean addAllFooterView(List<View> views) {
        boolean isAdd = footerViews.addAll(views);
        notifyDataSetChanged();
        return isAdd;
    }

    /**
     * Inserts all of the views at the specified position in this footer view
     * @param index
     * @param views
     * @return
     */
    public boolean addAllFooterView(int index, List<View> views) {
        boolean isAdd = footerViews.addAll(index, views);
        notifyDataSetChanged();
        return isAdd;
    }

    /**
     * Removes the specified view from this footer view
     * @param view
     * @return
     */
    public boolean removeFooterView(View view) {
        boolean isRemove = footerViews.remove(view);
        notifyDataSetChanged();
        return isRemove;
    }

    /**
     * Removes the specified view at the specified position in this footer view
     * @param index
     * @return
     */
    public View removeFooterView(int index) {
        View view = footerViews.remove(index);
        notifyDataSetChanged();
        return view;
    }

    /**
     * Removes all of the views from this footer view
     */
    public void removeAllFooterView() {
        footerViews.clear();
        notifyDataSetChanged();
    }

    /**
     * Removes from this footer view all of its views that are contained in the specified collection
     * @param views
     * @return
     */
    public boolean removeAllFooterView(List<View> views) {
        boolean isRemove = footerViews.removeAll(views);
        notifyDataSetChanged();
        return isRemove;
    }

    /**
     * Returns the total number of footer views by the adapter.
     * @return
     */
    public int getFooterViewsCount() {
        return footerViews.size();
    }

    /**
     * Returns a color integer associated with a particular resource ID. If the
     * resource holds a complex {@link ColorStateList}, then the default color
     * from the set is returned.
     *
     * @param id The desired resource identifier, as generated by the aapt
     *           tool. This integer encodes the package, type, and resource
     *           entry. The value 0 is an invalid identifier.
     *
     * @throws Resources.NotFoundException Throws NotFoundException if the given ID does
     *         not exist.
     *
     * @return A single color value in the form 0xAARRGGBB.
     * @deprecated Use {@link #getColor(int)} (int, Resources.Theme)} instead.
     */
    @ColorInt
    public int getColor(@android.support.annotation.ColorRes int id) {
        return context.getResources().getColor(id);
    }

    /**
     * Returns a localized string from the application's package's
     * default string table.
     *
     * @param resId Resource id for the string
     * @return The string data associated with the resource, stripped of styled
     *         text information.
     */
    @NonNull
    public String getString(@StringRes int resId) {
        return context.getString(resId);
    }

    /**
     * Returns a localized formatted string from the application's package's
     * default string table, substituting the format arguments as defined in
     * {@link java.util.Formatter} and {@link java.lang.String#format}.
     *
     * @param resId Resource id for the format string
     * @param formatArgs The format arguments that will be used for
     *                   substitution.
     * @return The string data associated with the resource, formatted and
     *         stripped of styled text information.
     */
    @NonNull
    public String getString(@StringRes int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

}
