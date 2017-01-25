package com.vincent.myrecyclerviewadapter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener, OnItemOtherClickListener {

    private RecyclerView recyclerView;
    private DemoAdapter2 demoAdapter;
    private DemoBean demoBean;
    private DemoBean demoBean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                int visibleItemCount = linearLayoutManager.getChildCount();
//                int totalItemCount = linearLayoutManager.getItemCount();
//                int firstVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
//                if (!isLoadMore && !isBottom) {
//                    if (queryAdapter.getItemCount() >= 8) {
//                        if ((visibleItemCount + firstVisibleItems) >= totalItemCount) {
////                            isLoadMore = true;
////                            currentPage++;
////                            NewsBean newsBean = new NewsBean();
////                            newsBean.setIsLoading(true);
////                            newsAdapter.add(newsBean);
////                            basePresenter.postNews(currentPage);
//                        }
//                    }
//                }
            }
        });
        ArrayList<DemoBean> demoBeans = new ArrayList<>();
        demoBeans.add(new DemoBean("a"));
        demoBeans.add(new DemoBean("b"));
        demoBean = new DemoBean("c");
        demoBeans.add(demoBean);
        demoBean2 = new DemoBean("d");
        demoBeans.add(demoBean2);
        demoBeans.add(new DemoBean("e"));
        demoBeans.add(new DemoBean("f"));
        demoBeans.add(new DemoBean("g"));
        demoBeans.add(new DemoBean("h"));
        demoBeans.add(new DemoBean("i"));
        demoBeans.add(new DemoBean("j"));
        demoBeans.add(new DemoBean("k"));
        demoAdapter = new DemoAdapter2(R.layout.item_demo, demoBeans);
        recyclerView.setAdapter(demoAdapter);
//        ItemTouchHelper.Callback callback = new BaseItemTouchHelperCallback<DemoBean>(demoAdapter, demoBeans);
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
//        demoAdapter.setItemTouchHelper(itemTouchHelper);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        demoAdapter.setOnItemClickListener(this);
        demoAdapter.setOnItemOtherClickListener(this);
        demoAdapter.setOnItemLongClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add) {
//            demoAdapter.add(new DemoBean(String.valueOf(new Random().nextInt(1000))));
//            demoAdapter.add(1, new DemoBean(String.valueOf(new Random().nextInt(1000))));
            ArrayList<DemoBean> demos = new ArrayList<>();
            demos.add(new DemoBean("22"));
            demos.add(new DemoBean("33"));
            demoAdapter.addAll(demos);
            return true;
        }
        if (id == R.id.remove) {
//            demoAdapter.remove(demoBean);
            ArrayList<DemoBean> demos = new ArrayList<>();
            demos.add(demoBean);
            demos.add(demoBean2);
            demoAdapter.removeAll(demos);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(ViewGroup parent, View v, int position) {
        switch (parent.getId()) {
            case R.id.recyclerView:
                Log.i("MainActivity", demoAdapter.getList().get(position).getStr());
                break;
        }
    }

    @Override
    public void onItemLongClick(ViewGroup parent, View v, int position) {
        switch (parent.getId()) {
            case R.id.recyclerView:
                Toast.makeText(this, demoAdapter.getList().get(position).getStr(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Log.i("MainActivity", "iv" + String.valueOf(position));
    }
}
