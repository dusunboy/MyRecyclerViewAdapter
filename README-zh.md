[ ![Blog](https://img.shields.io/badge/Blog-简书-orange.svg) ](http://www.jianshu.com/p/f32ef790e949)
[ ![Download](https://api.bintray.com/packages/dusunboy/maven/MyRecyclerViewAdapter/images/download.svg) ](https://bintray.com/dusunboy/maven/MyRecyclerViewAdapter/_latestVersion)

[English Version](README.md)

# MyRecyclerViewAdapter

> 我的RecyclerViewAdapter,支持左右滑动移除,拖拽,上拉加载,添加头部尾部

## 特性

支持LinearLayoutManager,GridLayoutManager,StaggeredGridLayoutManager

## 依赖

```groovy

dependencies {
    compile 'com.github.dusunboy.MyRecyclerViewAdapter:library:1.0.2'
}

```

## 使用
```java
    //单个Bean
    ArrayList<String> strings = new ArrayList<>();
    StringAdapter stringAdapter = new StringAdapter(R.layout.item_text_image, strings);

    public class StringAdapter extends BaseRecyclerViewAdapter<String, BaseViewHolder> {
         @Override
            protected void onBindView(final BaseViewHolder baseViewHolder, String s, final int position) {
                TextView tv = baseViewHolder.getView(R.id.tv);
                ...
            }
    }

    //多个Bean
    ArrayList<Object> list = new ArrayList<>();
    list.add(new DemoBean());
    list.add(new DemoImageBean());
    ListMultiAdapter listMultiAdapter = new ListMultiAdapter(list);
    public class ListMultiAdapter extends BaseMultiRecyclerViewAdapter<BaseViewHolder> {
     @Override
        public int getMyItemViewType(int position) {
            if (get(position) instanceof DemoBean) {
                return TEXT;
            } else {
                return IMAGE;
            }
        }

        @Override
        protected int getLayoutResId(int viewType) {
            switch (viewType) {
                case TEXT:
                    return R.layout.item_text;
                case IMAGE:
                    return R.layout.item_text_image;
                default:
                    return 0;
            }
        }


        @Override
        protected void onBindView(BaseViewHolder baseViewHolder, Object object, int position) {
            switch (baseViewHolder.getItemViewType()) {
                case TEXT:
                    bindTextView(baseViewHolder, (DemoBean) object, position);
                    break;
                case IMAGE:
                    bindImageView(baseViewHolder, (DemoImageBean) object, position);
                    break;
            }
        }
    }

    //添加头部尾部
    View item_header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
    View item_footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
    stringAdapter.addHeaderView(item_header);
    stringAdapter.addFooterView(item_footer);

    //点击，长按
    stringAdapter.setOnItemClickListener(this);
    stringAdapter.setOnItemLongClickListener(this);

    //item里的其他控件点击
    stringAdapter.setOnItemOtherClickListener(this);
    @Override
    protected void onBindView(final BaseViewHolder baseViewHolder, String s, final int position) {
        if (onItemOtherClickListener != null) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemOtherClickListener.OnItemOtherViewClick(baseViewHolder.itemView, v, position);
                }
            });
        }
    }

    //拖拽和左右滑动删除
    BaseItemTouchHelperCallback<String> baseItemTouchHelperCallback = new BaseItemTouchHelperCallback<String>(stringAdapter, strings);
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(baseItemTouchHelperCallback);
    stringAdapter.setItemTouchHelper(itemTouchHelper);
    itemTouchHelper.attachToRecyclerView(recyclerView);
    @Override
    protected void onBindView(final BaseViewHolder baseViewHolder, String s, final int position) {
        iv.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (itemTouchHelper != null) {
                //触发拖拽
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.startDrag(baseViewHolder);
                }
            }
            return false;
        }
        });
    }

    //上拉加载
    stringAdapter.setOnLoadMoreListener(this);
    //底部加载条是否显示
    stringAdapter.setLoading(true);
    //自定义底部加载条
    stringAdapter.setLoadingView(loadingView);
    //设置spanSizeLookup
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

```
## 演示

![demo](gif/device-2017-02-09-004237.gif?raw=true)

## 更新日志

### V1.0.1 2017-02-20

* 增加spanSizeLookup设置

## 许可证

    The MIT License (MIT)

    Copyright (c) [2015] [dusunboy]

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
