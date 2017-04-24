package com.example.lenovo.myapp.ui.activity.test.themetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.adapter.MyToolsAdapter;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.style.Theme_Translucent;
import static android.R.style.Theme_Wallpaper;
import static com.example.lenovo.myapp.ui.activity.test.themetest.ThemeDialogActivity.THEME_TYPE;

/**
 * 主题测试
 */

public class ThemeListActivity extends BaseActivity {

    private RecyclerView rvTheme;
    private List<String> list;
    private MyToolsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_test);

        initView();
        setData();

    }

    private void initView() {
        rvTheme = (RecyclerView) findViewById(R.id.rv_theme);
    }

    private void setData() {
        String[] themeNames = {
                getString(R.string.text_theme_name_1),
                getString(R.string.text_theme_name_2),
                getString(R.string.text_theme_name_3)
        };

        list = new ArrayList<>();
        list.clear();
        Collections.addAll(list, themeNames);

        mAdapter = new MyToolsAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvTheme.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvTheme.setAdapter(mAdapter);
    }

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {

        }

        @Override
        public void onTagClick(int tag, int position) {
            if (tag == TEXTVIEW) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(ThemeListActivity.this, ThemeDialogActivity.class);
                        break;
                    case 1:
                        intent.setClass(ThemeListActivity.this, ThemeTranslucentActivity.class);
                        intent.putExtra(THEME_TYPE, Theme_Translucent);
                        break;
                    case 2:
                        intent.setClass(ThemeListActivity.this, ThemeWallpaperActivity.class);
                        intent.putExtra(THEME_TYPE, Theme_Wallpaper);
                        break;
                }
                startActivity(intent);
            }
        }
    };
}
