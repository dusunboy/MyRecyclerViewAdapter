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

import com.myrecyclerviewadapter.vincent.app.adapter.StringAdapter;
import com.myrecyclerviewadapter.vincent.lib.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2017/2/6.
 */
public class SwipeRefreshListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private static final int INIT = 0;
    private static final int REFRESH = 1;
    private static final int LOADING = 2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private StringAdapter stringAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("SwipeRefreshListActivity");
        setContentView(R.layout.activity_swipe_refresh_list);
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
        ArrayList<String> strings = new ArrayList<>();
        stringAdapter = new StringAdapter(R.layout.item_text_image, strings);
        stringAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(stringAdapter);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        stringAdapter.addHeaderView(item_header);
        View item_footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        stringAdapter.addFooterView(item_footer);
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
                    for (int i = 0; i < 20; i++) {
                        strings.add("List View data: " + String.valueOf(i) +
                                "->" + String.valueOf(random.nextInt(1000)));
                    }
                    stringAdapter.addAll(strings);
                    break;
                case REFRESH:
                    swipeRefreshLayout.setRefreshing(false);
                    random = new Random();
                    String string = "Refresh List View data: " +
                            "->" + String.valueOf(random.nextInt(1000));
                    stringAdapter.add(0, string);
                    stringAdapter.notifyDataSetChanged();
                    break;
                case LOADING:
                    if (stringAdapter.getItemCount() >= 28) {
                        stringAdapter.setLoadMoreEnable(false);
                        return;
                    }
                    random = new Random();
                    strings = new ArrayList<>();
                    for (int i = 0; i < 1; i++) {
                        strings.add("Loading List View data: " + String.valueOf(i) +
                                "->" + String.valueOf(random.nextInt(1000)));
                    }
                    stringAdapter.addAll(strings);
                    stringAdapter.setLoading(false);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        if (!stringAdapter.isLoading()) {
            handler.sendEmptyMessageDelayed(REFRESH, 1000);
        }
    }

    @Override
    public void onLoadMore() {
        if (!swipeRefreshLayout.isRefreshing()) {
            stringAdapter.setLoading(true);
            handler.sendEmptyMessageDelayed(LOADING, 1000);
            recyclerView.scrollToPosition(stringAdapter.getItemCount() - 1);
        }
    }
}
