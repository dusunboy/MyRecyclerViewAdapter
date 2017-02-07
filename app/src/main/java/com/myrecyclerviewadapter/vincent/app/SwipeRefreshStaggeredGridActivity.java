package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.myrecyclerviewadapter.vincent.app.adapter.StaggeredGridAdapter;
import com.myrecyclerviewadapter.vincent.lib.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2017/2/7.
 */
public class SwipeRefreshStaggeredGridActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private static final int INIT = 0;
    private static final int REFRESH = 1;
    private static final int LOADING = 2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private StaggeredGridAdapter staggeredGridAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("SwipeRefreshStaggeredGridActivity");
        setContentView(R.layout.activity_swipe_refresh_grid);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        //Does not do anything to hide gaps.
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> strings = new ArrayList<>();
        staggeredGridAdapter = new StaggeredGridAdapter(R.layout.item_staggered_grid, strings);
        staggeredGridAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(staggeredGridAdapter);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        staggeredGridAdapter.addHeaderView(item_header);
        initRefresh();
    }

    private void initRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        handler.sendEmptyMessageDelayed(INIT, 1500);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INIT:
                    swipeRefreshLayout.setRefreshing(false);
                    Random random = new Random();
                    ArrayList<String> strings = new ArrayList<>();
                    for (int i = 0; i < 21; i++) {
                        if (i % 2 == 0) {
                            strings.add("" + String.valueOf(i) +
                                    "->" + String.valueOf(random.nextInt(1000)));
                        } else {
                            strings.add("StaggeredGrid View data: " + String.valueOf(i) +
                                    "->" + String.valueOf(random.nextInt(1000)));
                        }
                    }
                    staggeredGridAdapter.addAll(strings);
                    break;
                case REFRESH:
                    swipeRefreshLayout.setRefreshing(false);
                    random = new Random();
                    String string = "Refresh StaggeredGrid View data: " +
                            "->" + String.valueOf(random.nextInt(1000));
                    staggeredGridAdapter.add(0, string);
                    staggeredGridAdapter.notifyDataSetChanged();
                    break;
                case LOADING:
                    if (staggeredGridAdapter.getItemCount() >= 28) {
                        staggeredGridAdapter.setLoadMoreEnable(false);
                        return;
                    }
                    random = new Random();
                    strings = new ArrayList<>();
                    for (int i = 0; i < 1; i++) {
                        strings.add("Loading StaggeredGrid View data: " + String.valueOf(i) +
                                "->" + String.valueOf(random.nextInt(1000)));
                    }
                    staggeredGridAdapter.addAll(strings);
                    staggeredGridAdapter.setLoading(false);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        if (!staggeredGridAdapter.isLoading()) {
            handler.sendEmptyMessageDelayed(REFRESH, 1000);
        }
    }

    @Override
    public void onLoadMore() {
        if (!swipeRefreshLayout.isRefreshing()) {
            staggeredGridAdapter.setLoading(true);
            handler.sendEmptyMessageDelayed(LOADING, 1000);
            recyclerView.scrollToPosition(staggeredGridAdapter.getItemCount() - 1);
        }
    }
}
