package com.myrecyclerviewadapter.vincent.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myrecyclerviewadapter.vincent.app.adapter.StringAdapter;
import com.myrecyclerviewadapter.vincent.lib.BaseItemTouchHelperCallback;
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

        setTitle("GridActivity");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> strings = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            strings.add("Grid View data: " + String.valueOf(i) +
                    "->" + String.valueOf(random.nextInt(1000)));
        }
        stringAdapter = new StringAdapter(R.layout.item_text_image, strings);
        View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        View item_footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        stringAdapter.addHeaderView(item_header);
        stringAdapter.addFooterView(item_footer);
        //drag and swipe
        BaseItemTouchHelperCallback<String> baseItemTouchHelperCallback = new BaseItemTouchHelperCallback<String>(stringAdapter, strings);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(baseItemTouchHelperCallback);
        stringAdapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(stringAdapter);
        stringAdapter.setOnItemClickListener(this);
        stringAdapter.setOnItemLongClickListener(this);
        stringAdapter.setOnItemOtherClickListener(this);
        stringAdapter.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 4) {
                    return gridLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        Toast.makeText(this, "onItemClick:" + stringAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemOtherViewClick(View parent, View v, int position) {
        Toast.makeText(this, "onItemOtherViewClick:" + stringAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(ViewGroup parent, View v, int position) {
        Toast.makeText(this, "onItemLongClick:" + stringAdapter.get(position),
                Toast.LENGTH_SHORT).show();
    }
}
