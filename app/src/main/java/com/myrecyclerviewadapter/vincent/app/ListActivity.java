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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class ListActivity extends AppCompatActivity implements OnItemClickListener, OnItemOtherViewClickListener, OnItemLongClickListener {

    private StringAdapter stringAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle("ListActivity");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<String> strings = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            strings.add("List View data: " + String.valueOf(i) +
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
