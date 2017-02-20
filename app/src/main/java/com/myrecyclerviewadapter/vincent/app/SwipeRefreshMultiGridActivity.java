package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.myrecyclerviewadapter.vincent.app.adapter.GridMultiAdapter;
import com.myrecyclerviewadapter.vincent.app.model.DemoBean;
import com.myrecyclerviewadapter.vincent.app.model.DemoImageBean;
import com.myrecyclerviewadapter.vincent.lib.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2017/2/7.
 */
public class SwipeRefreshMultiGridActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private static final int INIT = 0;
    private static final int REFRESH = 1;
    private static final int LOADING = 2;
    private RecyclerView recyclerView;
    private GridMultiAdapter gridMultiAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_list);

        setTitle("SwipeRefreshMultiGridActivity");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<Object> list = new ArrayList<>();
        gridMultiAdapter = new GridMultiAdapter(list);
        gridMultiAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(gridMultiAdapter);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        gridMultiAdapter.addHeaderView(item_header);
        View loadingView = LayoutInflater.from(this).inflate(R.layout.item_text, null);
        TextView tv = (TextView) loadingView.findViewById(R.id.tv);
        tv.setText("loading...");
        gridMultiAdapter.setLoadingView(loadingView);
        gridMultiAdapter.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 4) {
                    return gridLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
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
                    for (int i = 0; i < 6; i++) {
                        DemoBean demoBean = new DemoBean();
                        demoBean.setString("Grid Multi View data: " + String.valueOf(i) +
                                "->" + String.valueOf(random.nextInt(1000)));
                        list.add(demoBean);
                    }
                    for (int i = 0; i < 12; i++) {
                        DemoImageBean demoImageBean = new DemoImageBean();
                        demoImageBean.setString("Grid Multi Image View data: " + String.valueOf(i) +
                                "->" + String.valueOf(random.nextInt(1000)));
                        list.add(demoImageBean);
                    }
                    gridMultiAdapter.addAll(list);
                    break;
                case REFRESH:
                    swipeRefreshLayout.setRefreshing(false);
                    random = new Random();
                    DemoBean demoBean = new DemoBean();
                    demoBean.setString("Refresh Grid Multi data: " +
                            "->" + String.valueOf(random.nextInt(1000)));
                    gridMultiAdapter.add(0, demoBean);
                    gridMultiAdapter.notifyDataSetChanged();
                    break;
                case LOADING:
                    if (gridMultiAdapter.getItemCount() >= 28) {
                        gridMultiAdapter.setLoadMoreEnable(false);
                        return;
                    }
                    random = new Random();
                    demoBean = new DemoBean();
                    demoBean.setString("Loading Grid Multi data: " +
                            "->" + String.valueOf(random.nextInt(1000)));
                    gridMultiAdapter.add(demoBean);
                    gridMultiAdapter.setLoading(false);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        if (!gridMultiAdapter.isLoading()) {
            handler.sendEmptyMessageDelayed(REFRESH, 1000);
        }
    }

    @Override
    public void onLoadMore() {
        if (!swipeRefreshLayout.isRefreshing()) {
            gridMultiAdapter.setLoading(true);
            handler.sendEmptyMessageDelayed(LOADING, 1000);
            recyclerView.scrollToPosition(gridMultiAdapter.getItemCount() - 1);
        }
    }
}
