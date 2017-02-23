package com.example.lenovo.myapp.ui.adapter;

/**
 * RecyclerView Adapter 的点击回调
 */

public interface OnListClickListener {

    //Tag类型（可以随便添加新类型）
    enum Tag {
        BUTTON1,
        BUTTON2,
        TEXTVIEW1,
        TEXTVIEW2,
        LINEARLAYOUT,
        LONGCLICK
    }

    //item点击事件
    void onItemClick(int position);

    //可根据tag来区分点击的是item内部哪个控件
    void onTagClick(Tag tag, int position);
}
