package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myrecyclerviewadapter.vincent.app.adapter.GridMultiAdapter;
import com.myrecyclerviewadapter.vincent.app.model.DemoBean;
import com.myrecyclerviewadapter.vincent.app.model.DemoImageBean;
import com.myrecyclerviewadapter.vincent.lib.OnItemClickListener;
import com.myrecyclerviewadapter.vincent.lib.OnItemLongClickListener;
import com.myrecyclerviewadapter.vincent.lib.OnItemOtherViewClickListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2017/2/6.
 */
public class GridMultiActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener, OnItemOtherViewClickListener {

    private RecyclerView recyclerView;
    private GridMultiAdapter gridMultiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle("GridMultiActivity");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Random random = new Random();
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            DemoBean demoBean = new DemoBean();
            demoBean.setString("List View data: " + String.valueOf(i) +
                    "->" + String.valueOf(random.nextInt(1000)));
            list.add(demoBean);
        }
        for (int i = 0; i < 12; i++) {
            DemoImageBean demoImageBean = new DemoImageBean();
            demoImageBean.setString("List Image View data: " + String.valueOf(i) +
                    "->" + String.valueOf(random.nextInt(1000)));
            list.add(demoImageBean);
        }
        gridMultiAdapter = new GridMultiAdapter(list);
        recyclerView.setAdapter(gridMultiAdapter);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        View item_footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        gridMultiAdapter.addHeaderView(item_header);
        gridMultiAdapter.addFooterView(item_footer);
        gridMultiAdapter.setOnItemClickListener(this);
        gridMultiAdapter.setOnItemLongClickListener(this);
        gridMultiAdapter.setOnItemOtherClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        Object object = gridMultiAdapter.get(position);
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
        Object object = gridMultiAdapter.get(position);
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
        Object object = gridMultiAdapter.get(position);
        if (object instanceof DemoBean) {
            Toast.makeText(this, "OnItemOtherViewClick:" +((DemoBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "OnItemOtherViewClick:" + ((DemoImageBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}