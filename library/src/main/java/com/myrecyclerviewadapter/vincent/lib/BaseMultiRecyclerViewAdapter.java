package com.myrecyclerviewadapter.vincent.lib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by Vincent on 2017/1/20.
 */
public abstract class BaseMultiRecyclerViewAdapter<K extends BaseViewHolder> extends RecyclerView.Adapter<K>{

    private static final int LOADING_VIEW = 100003;
    protected List<Object> list;
    protected Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    protected OnItemOtherViewClickListener onItemOtherClickListener;
    protected ItemTouchHelper itemTouchHelper;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private View loadingView;
    private boolean loadMoreEnable;
    private boolean isLoadMore;

    public BaseMultiRecyclerViewAdapter(List<Object> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading) {
            if (position == getItemCount() - 1) {
                return LOADING_VIEW;
            }
        }
        return getMyItemViewType(position);
    }

    /**
     * Return the view type of the item at position for the purposes of view recycling.
     * @param position
     * @return
     */
    public abstract int getMyItemViewType(int position);

    /**
     * The layout to inflate for the object for this viewType
     *
     * @param viewType viewType return by getItemViewType()
     * @return the resource ID of a layout to inflate
     */
    protected abstract int getLayoutResId(int viewType);

    @Override
    public K onCreateViewHolder(final ViewGroup parent, int viewType) {
        K k;
        this.context = parent.getContext();
        layoutInflater = LayoutInflater.from(context);
        switch (viewType) {
            case LOADING_VIEW:
                if (loadingView != null) {
                    k = createBaseViewHolder(parent, loadingView);
                } else {
                    ProgressBar progressBar = new ProgressBar(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER;
                    layoutParams.bottomMargin = dp2px(5);
                    progressBar.setLayoutParams(layoutParams);
                    k = createBaseViewHolder(parent, progressBar);
                }
                break;
            default:
                k = createBaseViewHolder(parent, getItemView(parent, getLayoutResId(viewType)));
                break;
        }
        return k;
    }

    @Override
    public void onBindViewHolder(final K k, int position) {
        int viewType = k.getItemViewType();
        if (viewType != LOADING_VIEW) {
            onBindView(k, list.get(position), position);
            k.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(k.getParent(), v, k.getAdapterPosition());
                    }
                }
            });
            k.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(k.getParent(), v, k.getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + (isLoading ? 1 : 0);
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    return (viewType == LOADING_VIEW) ? gridManager.getSpanCount() : 1;
                }
            });
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public int firstVisibleItems;
            public int totalItemCount;
            public int visibleItemCount;
            public int dy;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                this.dy = dy;
                if (manager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = getItemCount();
                    firstVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
                } else if (manager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) manager);
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = getItemCount();
                    firstVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                } else if (manager instanceof StaggeredGridLayoutManager) {

                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (loadMoreEnable && dy > 0) {
                    if (!isLoadMore && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (firstVisibleItems != 0 && (visibleItemCount + firstVisibleItems) >= totalItemCount) {
                            isLoadMore = true;
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                        }
                    }
                }
            }
        });
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
     * {@link ItemTouchHelper} <br/>
     * Let the view holder know that this item is being moved or dragged
     */
    public void onItemSelected() {

    }

    /**
     * {@link ItemTouchHelper} <br/>
     * Tell the view holder it's time to restore the idle state
     */
    public void onItemClear() {

    }

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
     * @param object
     * @param position
     */
    protected abstract void onBindView(K k, Object object, int position);

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
    public List<Object> getList() {
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
    public Object get(int position) {
        return list.get(position);
    }

    /**
     * Lists that support this operation may place limitations on what elements may be added to this list. In particular, some lists will refuse to add null elements, and others will impose restrictions on the type of elements that may be added. List classes should clearly specify in their documentation any restrictions on what elements may be added.
     * @param object
     */
    public void add(Object object) {
        list.add(object);
        notifyItemInserted(list.size());
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation). Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     * @param position
     * @param object
     */
    public void add(int position, Object object) {
        list.add(position, object);
        notifyItemInserted(position);
    }

    /**
     * Appends all of the elements in the specified collection to the end of this list, in the order that they are returned by the specified collection's iterator (optional operation). The behavior of this operation is undefined if the specified collection is modified while the operation is in progress. (Note that this will occur if the specified collection is this list, and it's nonempty.)
     * @param object
     */
    public void addAll(List<Object> object) {
        list.addAll(object);
        notifyItemInserted(list.size());
    }

    /**
     * Inserts all of the elements in the specified collection into this list at the specified position (optional operation). Shifts the element currently at that position (if any) and any subsequent elements to the right (increases their indices). The new elements will appear in this list in the order that they are returned by the specified collection's iterator. The behavior of this operation is undefined if the specified collection is modified while the operation is in progress. (Note that this will occur if the specified collection is this list, and it's nonempty.)
     * @param position
     * @param object
     */
    public void addAll(int position, List<Object> object) {
        list.addAll(position, object);
        notifyItemRangeInserted(position, object.size());
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present (optional operation). If this list does not contain the element, it is unchanged. More formally, removes the element with the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))) (if such an element exists). Returns true if this list contained the specified element (or equivalently, if this list changed as a result of the call).
     * @param object
     */
    public void remove(Object object) {
        int position = list.indexOf(object);
        if (list.remove(object)) {
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
     * @param object
     */
    public void removeAll(List<Object> object) {
        if (list.removeAll(object)) {
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
     * {@link java.util.Formatter} and {@link String#format}.
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

    /**
     * Register a callback to be invoked when scroll to bottom.
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        loadMoreEnable = true;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * set Loading Mode
     * @param isLoading
     */
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (isLoading) {
            notifyItemInserted(getItemCount());
        } else {
            notifyDataSetChanged();
            isLoadMore = false;
        }
    }

    /**
     * get isLoading
     * @return
     */
    public boolean isLoading() {
        return isLoading;
    }

    /**
     * dp convert px
     * @param dpVal
     * @return
     */
    private int dp2px(float dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * set loading view
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }

    /**
     * Enable {@link OnLoadMoreListener}
     * @param loadMoreEnable
     */
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
        if (!loadMoreEnable && isLoading) {
            setLoading(false);
        }
    }
}
