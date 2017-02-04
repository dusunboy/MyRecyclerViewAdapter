package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myrecyclerviewadapter.vincent.app.adapter.StringAdapter;
import com.myrecyclerviewadapter.vincent.lib.OnItemClickListener;
import com.myrecyclerviewadapter.vincent.lib.OnItemLongClickListener;
import com.myrecyclerviewadapter.vincent.lib.OnItemOtherViewClickListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2017/2/4.
 */
public class GridActivity extends AppCompatActivity implements OnItemClickListener, OnItemOtherViewClickListener, OnItemLongClickListener {

    private StringAdapter stringAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> strings = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            strings.add("Grid View data: " + String.valueOf(i) +
                    "->" + String.valueOf(random.nextInt(1000)));
        }
        stringAdapter = new StringAdapter(R.layout.item_text_image, strings);
        recyclerView.setAdapter(stringAdapter);
        stringAdapter.setOnItemClickListener(this);
        stringAdapter.setOnItemLongClickListener(this);
        stringAdapter.setOnItemOtherClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        Toast.makeText(this, "onItemClick:" + stringAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemOtherViewClick(View parent, View v, int position) {
        Toast.makeText(this, "OnItemOtherViewClick:" + stringAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(ViewGroup parent, View v, int position) {
        Toast.makeText(this, "onItemLongClick:" + stringAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }
}
