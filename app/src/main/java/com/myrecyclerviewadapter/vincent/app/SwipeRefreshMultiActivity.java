package com.myrecyclerviewadapter.vincent.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.myrecyclerviewadapter.vincent.app.adapter.StringAdapter;
import com.myrecyclerviewadapter.vincent.lib.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by Vincent on 2017/2/7.
 */

public class SwipeRefreshMultiActivity extends AppCompatActivity implements OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("SwipeRefreshMultiActivity");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> strings = new ArrayList<>();
        strings.add("SwipeRefresh Multi List View");
        strings.add("SwipeRefresh Multi Grid View");
        strings.add("SwipeRefresh Multi StaggeredGrid View");
        StringAdapter stringAdapter = new StringAdapter(R.layout.item_text, strings);
        recyclerView.setAdapter(stringAdapter);
        stringAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, SwipeRefreshMultiListActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, SwipeRefreshMultiGridActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, SwipeRefreshMultiStaggeredGridActivity.class));
                break;
        }
    }
}
