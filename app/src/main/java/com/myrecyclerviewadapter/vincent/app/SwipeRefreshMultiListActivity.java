package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.myrecyclerviewadapter.vincent.app.adapter.ListMultiAdapter;
import com.myrecyclerviewadapter.vincent.app.model.DemoBean;
import com.myrecyclerviewadapter.vincent.app.model.DemoImageBean;
import com.myrecyclerviewadapter.vincent.lib.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2017/2/7.
 */
public class SwipeRefreshMultiListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private static final int INIT = 0;
    private static final int REFRESH = 1;
    private static final int LOADING = 2;
    private RecyclerView recyclerView;
    private ListMultiAdapter listMultiAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_list);

        setTitle("SwipeRefreshMultiListActivity");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<Object> list = new ArrayList<>();
        listMultiAdapter = new ListMultiAdapter(list);
        listMultiAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(listMultiAdapter);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        listMultiAdapter.addHeaderView(item_header);
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
                    for (int i = 0; i < 3; i++) {
                        DemoBean demoBean = new DemoBean();
                        demoBean.setString("List Multi View data: " + String.valueOf(i) +
                                "->" + String.valueOf(random.nextInt(1000)));
                        list.add(demoBean);
                    }
                    for (int i = 0; i < 6; i++) {
                        DemoImageBean demoImageBean = new DemoImageBean();
                        demoImageBean.setString("List Multi Image View data: " + String.valueOf(i) +
                                "->" + String.valueOf(random.nextInt(1000)));
                        list.add(demoImageBean);
                    }
                    listMultiAdapter.addAll(list);
                    break;
                case REFRESH:
                    swipeRefreshLayout.setRefreshing(false);
                    random = new Random();
                    DemoBean demoBean = new DemoBean();
                    demoBean.setString("Refresh List Multi data: " +
                            "->" + String.valueOf(random.nextInt(1000)));
                    listMultiAdapter.add(0, demoBean);
                    listMultiAdapter.notifyDataSetChanged();
                    break;
                case LOADING:
                    if (listMultiAdapter.getItemCount() >= 18) {
                        listMultiAdapter.setLoadMoreEnable(false);
                        return;
                    }
                    random = new Random();
                    demoBean = new DemoBean();
                    demoBean.setString("Loading List Multi data: " +
                            "->" + String.valueOf(random.nextInt(1000)));
                    listMultiAdapter.add(demoBean);
                    listMultiAdapter.setLoading(false);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        if (!listMultiAdapter.isLoading()) {
            handler.sendEmptyMessageDelayed(REFRESH, 1000);
        }
    }

    @Override
    public void onLoadMore() {
        if (!swipeRefreshLayout.isRefreshing()) {
            listMultiAdapter.setLoading(true);
            handler.sendEmptyMessageDelayed(LOADING, 1000);
            recyclerView.scrollToPosition(listMultiAdapter.getItemCount() - 1);
        }
    }
}
