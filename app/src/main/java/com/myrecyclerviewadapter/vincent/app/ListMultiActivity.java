package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myrecyclerviewadapter.vincent.app.adapter.ListMultiAdapter;
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

public class ListMultiActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener, OnItemOtherViewClickListener {

    private RecyclerView recyclerView;
    private ListMultiAdapter listMultiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle("ListMultiActivity");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Random random = new Random();
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DemoBean demoBean = new DemoBean();
            demoBean.setString("List View data: " + String.valueOf(i) +
                    "->" + String.valueOf(random.nextInt(1000)));
            list.add(demoBean);
        }
        for (int i = 0; i < 6; i++) {
            DemoImageBean demoImageBean = new DemoImageBean();
            demoImageBean.setString("List Image View data: " + String.valueOf(i) +
                    "->" + String.valueOf(random.nextInt(1000)));
            list.add(demoImageBean);
        }
        listMultiAdapter = new ListMultiAdapter(list);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        View item_footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        listMultiAdapter.addHeaderView(item_header);
        listMultiAdapter.addFooterView(item_footer);
        recyclerView.setAdapter(listMultiAdapter);
        listMultiAdapter.setOnItemClickListener(this);
        listMultiAdapter.setOnItemLongClickListener(this);
        listMultiAdapter.setOnItemOtherClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        Object object = listMultiAdapter.get(position);
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
        Object object = listMultiAdapter.get(position);
        if (object instanceof DemoBean) {
            Toast.makeText(this, "onItemLongClick:" + ((DemoBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "onItemLongClick:" + ((DemoImageBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemOtherViewClick(View parent, View v, int position) {
        Object object = listMultiAdapter.get(position);
        if (object instanceof DemoBean) {
            Toast.makeText(this, "onItemOtherViewClick:" +((DemoBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "onItemOtherViewClick:" + ((DemoImageBean) object).getString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
