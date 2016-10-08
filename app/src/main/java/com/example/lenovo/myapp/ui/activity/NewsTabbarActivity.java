package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.cxb.tools.NewsTab.HorizontalTabListScrollView;
import com.cxb.tools.NewsTab.NewsTab;
import com.cxb.tools.NewsTab.NewsTabResoureUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;
import com.cxb.tools.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻头部列表
 */

public class NewsTabbarActivity extends BaseActivity {

    private HorizontalTabListScrollView svNewsTabs;
    private List<NewsTab> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_tabbar);

        initView();

    }

    private void initView(){
        svNewsTabs = (HorizontalTabListScrollView) findViewById(R.id.htlsv_list);
        svNewsTabs.setOnItemSelectedListener(new HorizontalTabListScrollView.OnItemSelectedListener() {
            @Override
            public void onItemClick(View v, int postion) {
                ToastUtil.toast(list.get(postion).getName());
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(new NewsTab(String.valueOf(i), NewsTabResoureUtil.name[i]));
        }
        svNewsTabs.addTabList(list);
    }
}
