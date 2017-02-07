package com.myrecyclerviewadapter.vincent.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.myrecyclerviewadapter.vincent.app.adapter.StringAdapter;
import com.myrecyclerviewadapter.vincent.lib.OnItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> strings = new ArrayList<>();
        strings.add("List View");
        strings.add("Grid View");
        strings.add("StaggeredGrid View");
        strings.add("List Multi View");
        strings.add("Grid Multi View");
        strings.add("StaggeredGrid Multi View");
        strings.add("SwipeRefreshLayout View");
        strings.add("SwipeRefreshLayout Multi View");
        StringAdapter stringAdapter = new StringAdapter(R.layout.item_text, strings);
        recyclerView.setAdapter(stringAdapter);
        stringAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, ListActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, GridActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, StaggeredGridActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, ListMultiActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, GridMultiActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, StaggeredGridMultiActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, SwipeRefreshActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, SwipeRefreshMultiActivity.class));
                break;
        }
    }
}
