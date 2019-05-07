package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.myrecyclerviewadapter.vincent.app.adapter.StaggeredGridAdapter;
import com.myrecyclerviewadapter.vincent.lib.BaseItemTouchHelperCallback;
import com.myrecyclerviewadapter.vincent.lib.OnItemClickListener;
import com.myrecyclerviewadapter.vincent.lib.OnItemLongClickListener;
import com.myrecyclerviewadapter.vincent.lib.OnItemOtherViewClickListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2017/2/7.
 */
public class StaggeredGridActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener, OnItemOtherViewClickListener {

    private StaggeredGridAdapter staggeredGridAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle("StaggeredGridActivity");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> strings = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 21; i++) {
            if (i % 2 == 0) {
                strings.add("" + String.valueOf(i) +
                        "->" + String.valueOf(random.nextInt(1000)));
            } else {
                strings.add("List View data: " + String.valueOf(i) +
                        "->" + String.valueOf(random.nextInt(1000)));
            }
        }
        staggeredGridAdapter = new StaggeredGridAdapter(R.layout.item_staggered_grid, strings);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        View item_footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        staggeredGridAdapter.addHeaderView(item_header);
        staggeredGridAdapter.addFooterView(item_footer);
        //drag and swipe
        BaseItemTouchHelperCallback<String> baseItemTouchHelperCallback = new BaseItemTouchHelperCallback<String>(staggeredGridAdapter, strings);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(baseItemTouchHelperCallback);
        staggeredGridAdapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(staggeredGridAdapter);
        staggeredGridAdapter.setOnItemClickListener(this);
        staggeredGridAdapter.setOnItemLongClickListener(this);
        staggeredGridAdapter.setOnItemOtherClickListener(this);
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        Toast.makeText(this, "onItemClick:" + staggeredGridAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemOtherViewClick(View parent, View v, int position) {
        Toast.makeText(this, "onItemOtherViewClick:" + staggeredGridAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(ViewGroup parent, View v, int position) {
        Toast.makeText(this, "onItemLongClick:" + staggeredGridAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }
}
