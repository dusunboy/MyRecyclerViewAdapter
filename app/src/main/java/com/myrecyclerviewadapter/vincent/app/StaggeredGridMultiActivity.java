package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myrecyclerviewadapter.vincent.app.adapter.StaggeredGridMultiAdapter;
import com.myrecyclerviewadapter.vincent.app.model.DemoBean;
import com.myrecyclerviewadapter.vincent.app.model.DemoImageBean;
import com.myrecyclerviewadapter.vincent.lib.OnItemClickListener;
import com.myrecyclerviewadapter.vincent.lib.OnItemLongClickListener;
import com.myrecyclerviewadapter.vincent.lib.OnItemOtherViewClickListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2017/2/7.
 */
public class StaggeredGridMultiActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener, OnItemOtherViewClickListener {

    private RecyclerView recyclerView;
    private StaggeredGridMultiAdapter staggeredGridMultiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle("StaggeredGridMultiActivity");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Random random = new Random();
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            if (i % 2 == 0) {
                DemoBean demoBean = new DemoBean();
                demoBean.setString("List View data: " + String.valueOf(i) +
                        "->" + String.valueOf(random.nextInt(1000)));
                list.add(demoBean);
            } else {
                DemoImageBean demoImageBean = new DemoImageBean();
                demoImageBean.setString("List Image View data: " + String.valueOf(i) +
                        "->" + String.valueOf(random.nextInt(1000)));
                list.add(demoImageBean);
            }

        }
        staggeredGridMultiAdapter = new StaggeredGridMultiAdapter(list);
        recyclerView.setAdapter(staggeredGridMultiAdapter);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        View item_footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        staggeredGridMultiAdapter.addHeaderView(item_header);
        staggeredGridMultiAdapter.addFooterView(item_footer);
        staggeredGridMultiAdapter.setOnItemClickListener(this);
        staggeredGridMultiAdapter.setOnItemLongClickListener(this);
        staggeredGridMultiAdapter.setOnItemOtherClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        Object object = staggeredGridMultiAdapter.get(position);
        if (object instanceof DemoBean) {
            Toast.makeText(this, "onItemClick:" + ((DemoBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "onItemClick:" + ((DemoImageBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemLongClick(ViewGroup parent, View v, int position) {
        Object object = staggeredGridMultiAdapter.get(position);
        if (object instanceof DemoBean) {
            Toast.makeText(this, "onItemLongClick:" + ((DemoBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "onItemLongClick:" + ((DemoImageBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnItemOtherViewClick(View parent, View v, int position) {
        Object object = staggeredGridMultiAdapter.get(position);
        if (object instanceof DemoBean) {
            Toast.makeText(this, "onItemOtherViewClick:" +((DemoBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "onItemOtherViewClick:" + ((DemoImageBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
