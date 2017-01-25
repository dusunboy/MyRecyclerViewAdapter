package com.vincent.myrecyclerviewadapter;

/**
 * Created by Administrator on 2017/1/20.
 */
class DemoBean {
    private String str;

    DemoBean(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return "DemoBean{" +
                "str='" + str + '\'' +
                '}';
    }
}
