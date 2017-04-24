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
import static android.R.style.Theme_Translucent_NoTitleBar;
import static android.R.style.Theme_Translucent_NoTitleBar_Fullscreen;

/**
 * 对话框主题
 */

public class ThemeTranslucentActivity extends BaseActivity {

    public static final String THEME_TYPE = "theme_type";

    private RecyclerView rvTheme;
    private List<String> list;
    private MyToolsAdapter mAdapter;

    private int currentThemeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_detail);

        initView();
        setData();

    }

    @Override
    public void setTheme(int resid) {
        currentThemeType = getIntent().getIntExtra(THEME_TYPE, Theme_Translucent);
        super.setTheme(currentThemeType);
    }

    private void initView() {
        rvTheme = (RecyclerView) findViewById(R.id.rv_theme);
    }

    private void setData() {
        String[] themeNames = {
                getString(R.string.btn_translucent_theme_1),
                getString(R.string.btn_translucent_theme_2)
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
                intent.setClass(ThemeTranslucentActivity.this, ThemeTranslucentActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra(THEME_TYPE, Theme_Translucent_NoTitleBar);
                        break;
                    case 1:
                        intent.putExtra(THEME_TYPE, Theme_Translucent_NoTitleBar_Fullscreen);
                        break;
                }
                startActivity(intent);
                finish();
            }
        }
    };

}
