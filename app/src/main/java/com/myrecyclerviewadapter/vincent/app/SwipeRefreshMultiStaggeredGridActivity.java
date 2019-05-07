package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.myrecyclerviewadapter.vincent.app.adapter.StaggeredGridMultiAdapter;
import com.myrecyclerviewadapter.vincent.app.model.DemoBean;
import com.myrecyclerviewadapter.vincent.app.model.DemoImageBean;
import com.myrecyclerviewadapter.vincent.lib.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2017/2/8.
 */
public class SwipeRefreshMultiStaggeredGridActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private static final int INIT = 0;
    private static final int REFRESH = 1;
    private static final int LOADING = 2;
    private RecyclerView recyclerView;
    private StaggeredGridMultiAdapter staggeredGridMultiAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_grid);

        setTitle("SwipeRefreshMultiStaggeredGridActivity");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Random random = new Random();
        ArrayList<Object> list = new ArrayList<>();
        staggeredGridMultiAdapter = new StaggeredGridMultiAdapter(list);
        staggeredGridMultiAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(staggeredGridMultiAdapter);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        staggeredGridMultiAdapter.addHeaderView(item_header);
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
                    ArrayList<Object> list = new ArrayList<>();
                    for (int i = 0; i < 21; i++) {
                        if (i % 2 == 0) {
                            DemoBean demoBean = new DemoBean();
                            demoBean.setString("StaggeredGrid View data: " + String.valueOf(i) +
                                    "->" + String.valueOf(random.nextInt(1000)));
                            list.add(demoBean);
                        } else {
                            DemoImageBean demoImageBean = new DemoImageBean();
                            demoImageBean.setString("StaggeredGrid Image View data: " + String.valueOf(i) +
                                    "->" + String.valueOf(random.nextInt(1000)));
                            list.add(demoImageBean);
                        }
                    }
                    staggeredGridMultiAdapter.addAll(list);
                    break;
                case REFRESH:
                    swipeRefreshLayout.setRefreshing(false);
                    random = new Random();
                    DemoBean demoBean = new DemoBean();
                    demoBean.setString("Refresh StaggeredGrid View data: " +
                            "->" + String.valueOf(random.nextInt(1000)));
                    staggeredGridMultiAdapter.add(0, demoBean);
                    staggeredGridMultiAdapter.notifyDataSetChanged();
                    break;
                case LOADING:
                    if (staggeredGridMultiAdapter.getItemCount() >= 28) {
                        staggeredGridMultiAdapter.setLoadMoreEnable(false);
                        return;
                    }
                    random = new Random();
                    demoBean = new DemoBean();
                    demoBean.setString("Loading StaggeredGrid View data: " +
                            "->" + String.valueOf(random.nextInt(1000)));
                    staggeredGridMultiAdapter.add(demoBean);
                    staggeredGridMultiAdapter.setLoading(false);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        if (!staggeredGridMultiAdapter.isLoading()) {
            handler.sendEmptyMessageDelayed(REFRESH, 1000);
        }
    }

    @Override
    public void onLoadMore() {
        if (!swipeRefreshLayout.isRefreshing()) {
            staggeredGridMultiAdapter.setLoading(true);
            handler.sendEmptyMessageDelayed(LOADING, 1000);
            recyclerView.scrollToPosition(staggeredGridMultiAdapter.getItemCount() - 1);
        }
    }
}
