package com.example.lenovo.myapp.ui.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.activity.GuideActivity;
import com.example.lenovo.myapp.ui.activity.test.dbtest.DatebaseTestActivity;
import com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestTestActivity;
import com.example.lenovo.myapp.ui.activity.test.systemres.SystemResActivity;
import com.example.lenovo.myapp.ui.adapter.MyToolsAdapter;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 我的工具
 */

public class MyToolsActivity extends BaseActivity {

    private ImageView ivBack;
    private RecyclerView rvTools;
    private List<String> list;
    private MyToolsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tools);

        initView();
        setData();

    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvTools = (RecyclerView) findViewById(R.id.rv_tools);
    }

    private void setData() {
        ivBack.setOnClickListener(click);

        list = new ArrayList<>();
        mAdapter = new MyToolsAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvTools.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvTools.setAdapter(mAdapter);

        String[] toolNames = {
                getString(R.string.btn_tool_1),
                getString(R.string.btn_tool_2),
                getString(R.string.btn_tool_3),
                getString(R.string.btn_tool_4),
                getString(R.string.btn_tool_5),
                getString(R.string.btn_tool_6),
                getString(R.string.btn_tool_7),
                getString(R.string.btn_tool_8),
                getString(R.string.btn_tool_9),
                getString(R.string.btn_tool_10),
                getString(R.string.btn_tool_11),
                getString(R.string.btn_tool_12),
                getString(R.string.btn_tool_13)
        };

        list.clear();
        Collections.addAll(list, toolNames);
        mAdapter.notifyDataSetChanged();
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
            }
        }
    };

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {

        }

        @Override
        public void onTagClick(int tag, int position) {
            if (tag == TEXTVIEW) {
                switch (position) {
                    case 0://ViewPager简介
                        startActivity(new Intent(MyToolsActivity.this, GuideActivity.class));
                        break;
                    case 1://接口测试
                        startActivity(new Intent(MyToolsActivity.this, OkhttpTestActivity.class));
                        break;
                    case 2://动画测试
                        startActivity(new Intent(MyToolsActivity.this, AnimationTestActivity.class));
                        break;
                    case 3://对话框测试
                        startActivity(new Intent(MyToolsActivity.this, DialogTestActivity.class));
                        break;
                    case 4://线程池测试
                        startActivity(new Intent(MyToolsActivity.this, ThreadPoolTestActivity.class));
                        break;
                    case 5://日期工具测试
                        startActivity(new Intent(MyToolsActivity.this, DateTimeTestActivity.class));
                        break;
                    case 6://列表嵌套测试
                        startActivity(new Intent(MyToolsActivity.this, ListNestTestActivity.class));
                        break;
                    case 7://已安装APP信息
                        startActivity(new Intent(MyToolsActivity.this, AppInfoTestActivity.class));
                        break;
                    case 8://自定义控件展示
                        startActivity(new Intent(MyToolsActivity.this, CustomViewTestActivity.class));
                        break;
                    case 9://获取系统资源
                        startActivity(new Intent(MyToolsActivity.this, SystemResActivity.class));
                        break;
                    case 10://LiteOrm数据库工具s
                        startActivity(new Intent(MyToolsActivity.this, DatebaseTestActivity.class));
                        break;
                    case 11://WebView Demo
                        startActivity(new Intent(MyToolsActivity.this, WebViewTestActivity.class));
                        break;
                    case 12://多语言设置
                        startActivity(new Intent(MyToolsActivity.this, MultiLanguageActivity.class));
                        break;
                }
            }
        }
    };

}
