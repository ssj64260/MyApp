package com.example.lenovo.myapp.ui.activity.test;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxb.tools.utils.AppManager;
import com.cxb.tools.utils.FastClick;
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

    private View detail;
    private ImageView ivBack;
    private ImageView ivAppIcon;
    private TextView tvAppName;
    private Button btnShowDetail;
    private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info_test);

        initView();
        setData();

    }

    @Override
    public void onBackPressed() {
        if (detail.getVisibility() == View.GONE) {
            finish();
        } else {
            hideDetail();
        }
    }

    private void initView() {
        rvAppList = (RecyclerView) findViewById(R.id.rv_app_list);

        detail = findViewById(R.id.view_detail);
        ivBack = (ImageView) detail.findViewById(R.id.iv_back);
        ivAppIcon = (ImageView) detail.findViewById(R.id.iv_app_icon);
        tvAppName = (TextView) detail.findViewById(R.id.tv_app_name);
        btnDelete = (Button) detail.findViewById(R.id.btn_delete);
        btnShowDetail = (Button) detail.findViewById(R.id.btn_show_detail);
    }

    private void setData() {
        list = new ArrayList<>();
        list.addAll(AppManager.getInstallApkList(this));

        int count = list.size();
        for (int i = 0; i < count; i++) {
            PackageInfo info = list.get(i);
            if (getPackageName().contains(info.applicationInfo.packageName)) {
                list.remove(info);
                list.add(0, info);
                break;
            }
        }

        mAdapter = new AppInfoListAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvAppList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAppList.setAdapter(mAdapter);

        detail.setVisibility(View.GONE);
        ivBack.setOnClickListener(click);
    }

    private void setDetailData(final PackageInfo pi) {
        ivAppIcon.setImageDrawable(pi.applicationInfo.loadIcon(getPackageManager()));
        tvAppName.setText(pi.applicationInfo.loadLabel(getPackageManager()).toString());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.startUninstall(AppInfoTestActivity.this, pi.packageName);
            }
        });
        btnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.showInstalledAppDetails(AppInfoTestActivity.this, pi.packageName);
            }
        });
    }

    private void showDetail() {
        if (!FastClick.isFastClick()) {
            AlphaAnimation mShowAction = new AlphaAnimation(0, 1);
            mShowAction.setDuration(500);
            detail.setAnimation(mShowAction);

            detail.setVisibility(View.VISIBLE);
            rvAppList.setVisibility(View.GONE);
        }
    }

    private void hideDetail() {
        if (!FastClick.isFastClick()) {
            AlphaAnimation mHiddenAction = new AlphaAnimation(1, 0);
            mHiddenAction.setDuration(500);
            detail.setAnimation(mHiddenAction);

            detail.setVisibility(View.GONE);
            rvAppList.setVisibility(View.VISIBLE);
        }
    }


    //列表点击监听
    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            showDetail();
            setDetailData(list.get(position));
        }

        @Override
        public void onTagClick(int tag, int position) {

        }
    };

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    hideDetail();
                    break;
                case R.id.btn_delete:

                    break;
            }
        }
    };

}
