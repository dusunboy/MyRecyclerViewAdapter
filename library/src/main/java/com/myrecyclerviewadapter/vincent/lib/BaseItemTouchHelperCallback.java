/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myrecyclerviewadapter.vincent.lib;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@link ItemTouchHelper.Callback} that enables basic drag & drop and
 * swipe-to-dismiss. Drag events are automatically started by an item long-press.<br/>
 * </br/>
 * Expects the <code>RecyclerView.Adapter</code> to listen for {@link
 * BaseItemTouchHelperCallback} callbacks and the <code>RecyclerView.ViewHolder</code> to implement
 * {@link BaseItemTouchHelperCallback}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class BaseItemTouchHelperCallback<T> extends ItemTouchHelper.Callback {

    public static final float ALPHA_FULL = 1.0f;

    private final List<T> tList;
    private final BaseRecyclerViewAdapter adapter;

    public BaseItemTouchHelperCallback(BaseRecyclerViewAdapter adapter, ArrayList<T> tList) {
        this.adapter = adapter;
        this.tList = tList;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager
                || recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        int fromPosition = source.getAdapterPosition() - adapter.getHeaderViewsCount();
        int toPosition = target.getAdapterPosition() - adapter.getHeaderViewsCount();

        if (fromPosition < toPosition) {
            tList.add(toPosition + 1, tList.get(fromPosition));
            tList.remove(fromPosition);
        } else {
            T t = tList.remove(fromPosition);
            tList.add(toPosition, t);
        }
        adapter.notifyItemMoved(fromPosition + adapter.getHeaderViewsCount(),
                toPosition + adapter.getHeaderViewsCount());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition() - adapter.getHeaderViewsCount();
        tList.remove(position);
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Fade out the view as it is swiped out of the parent's bounds
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof BaseViewHolder) {
                // Let the view holder know that this item is being moved or dragged
                BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
                baseViewHolder.onBaseItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setAlpha(ALPHA_FULL);
        if (viewHolder instanceof BaseViewHolder) {
            // Tell the view holder it's time to restore the idle state
            BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
            baseViewHolder.onBaseItemClear();
        }
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 200);
        super.clearView(recyclerView, viewHolder);
    }
}
