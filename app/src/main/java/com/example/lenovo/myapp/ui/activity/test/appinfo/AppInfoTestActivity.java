package com.example.lenovo.myapp.ui.activity.test.appinfo;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cxb.tools.utils.AppManager;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.adapter.AppInfoListAdapter;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机安装APP信息
 */

public class AppInfoTestActivity extends BaseActivity {

    private RecyclerView rvAppList;
    private List<PackageInfo> list;
    private AppInfoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info_test);

        initView();

        setData();

    }

    private void initView() {
        rvAppList = (RecyclerView) findViewById(R.id.rv_app_list);
    }

    private void setData() {
        list = new ArrayList<>();
        list.addAll(AppManager.getInstallApkList(this));
        mAdapter = new AppInfoListAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvAppList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAppList.setAdapter(mAdapter);
    }

    //列表点击监听
    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {

        }

        @Override
        public void onTagClick(Tag tag, int position) {

        }
    };

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    };

}
