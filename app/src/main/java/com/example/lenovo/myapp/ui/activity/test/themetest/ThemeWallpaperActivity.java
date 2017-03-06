package com.example.lenovo.myapp.ui.activity.test.themetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.adapter.MyToolsAdapter;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.style.Theme_DeviceDefault_Wallpaper;
import static android.R.style.Theme_DeviceDefault_Wallpaper_NoTitleBar;
import static android.R.style.Theme_Holo_Wallpaper;
import static android.R.style.Theme_Holo_Wallpaper_NoTitleBar;
import static android.R.style.Theme_Light_WallpaperSettings;
import static android.R.style.Theme_Material_Wallpaper;
import static android.R.style.Theme_Material_Wallpaper_NoTitleBar;
import static android.R.style.Theme_Wallpaper;
import static android.R.style.Theme_WallpaperSettings;
import static android.R.style.Theme_Wallpaper_NoTitleBar;
import static android.R.style.Theme_Wallpaper_NoTitleBar_Fullscreen;

/**
 * 对话框主题
 */

public class ThemeWallpaperActivity extends BaseActivity {

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
        currentThemeType = getIntent().getIntExtra(THEME_TYPE, Theme_Wallpaper);
        super.setTheme(currentThemeType);
    }

    private void initView() {
        rvTheme = (RecyclerView) findViewById(R.id.rv_theme);
    }

    private void setData() {
        String[] themeNames = {
                getString(R.string.btn_wallpaper_theme_1),
                getString(R.string.btn_wallpaper_theme_2),
                getString(R.string.btn_wallpaper_theme_3),
                getString(R.string.btn_wallpaper_theme_4),
                getString(R.string.btn_wallpaper_theme_5),
                getString(R.string.btn_wallpaper_theme_6),
                getString(R.string.btn_wallpaper_theme_7),
                getString(R.string.btn_wallpaper_theme_8),
                getString(R.string.btn_wallpaper_theme_9),
                getString(R.string.btn_wallpaper_theme_10)
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
                intent.setClass(ThemeWallpaperActivity.this, ThemeWallpaperActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra(THEME_TYPE, Theme_Wallpaper_NoTitleBar);
                        break;
                    case 1:
                        intent.putExtra(THEME_TYPE, Theme_Wallpaper_NoTitleBar_Fullscreen);
                        break;
                    case 2:
                        intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Wallpaper);
                        break;
                    case 3:
                        intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Wallpaper_NoTitleBar);
                        break;
                    case 4:
                        intent.putExtra(THEME_TYPE, Theme_Holo_Wallpaper);
                        break;
                    case 5:
                        intent.putExtra(THEME_TYPE, Theme_Holo_Wallpaper_NoTitleBar);
                        break;
                    case 6:
                        intent.putExtra(THEME_TYPE, Theme_Material_Wallpaper);
                        break;
                    case 7:
                        intent.putExtra(THEME_TYPE, Theme_Material_Wallpaper_NoTitleBar);
                        break;
                    case 8:
                        intent.putExtra(THEME_TYPE, Theme_WallpaperSettings);
                        break;
                    case 9:
                        intent.putExtra(THEME_TYPE, Theme_Light_WallpaperSettings);
                        break;
                }
                startActivity(intent);
                finish();
            }
        }
    };

}
