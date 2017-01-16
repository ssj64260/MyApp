package com.example.lenovo.myapp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cxb.tools.Glide.GlideCircleTransform;
import com.cxb.tools.QQLevelLayout.QQLevelLayout;
import com.cxb.tools.SlidingMenu.MySlidingMenu;
import com.cxb.tools.SlidingMenu.SlidingMenuResUtil;
import com.cxb.tools.utils.DisplayUtil;
import com.cxb.tools.utils.NetworkUtil;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.QQMessageBean;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.QQMainAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 侧滑栏测试
 */

public class QQMainActivity extends BaseActivity {

    private final String[] titles = {"消息", "电话"};

    private MySlidingMenu smMain;
    private View leftView;
    private QQLevelLayout qqLevel;
    private ImageView ivBackground;
    private ImageView ivScan;
    private ImageView ivMenuAvatar;
    private TextView tvMenuName;
    private LinearLayout llSign;
    private TextView tvSign;

    private View rightView;
    private ImageView ivMainAvatar;//主页头像
    private SegmentTabLayout titleTab;
    private ImageView ivAdd;
    private RelativeLayout rlNetworkWarm;

    private XRecyclerView recyclerView;
    private List<QQMessageBean> list;
    private QQMainAdapter adapter;

    BroadcastReceiver checkNetwork = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int networkStatus = NetworkUtil.checkNetWorkType(QQMainActivity.this);

            switch (networkStatus) {
                case NetworkUtil.NETWORK_NONE:
                    rlNetworkWarm.setVisibility(View.VISIBLE);
                    break;
                case NetworkUtil.NETWORK_MOBILE:
                    rlNetworkWarm.setVisibility(View.GONE);
                    break;
                case NetworkUtil.NETWORK_WIFI:
                    rlNetworkWarm.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_main);

        initView();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkNetwork, intentFilter);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(checkNetwork);
        super.onDestroy();
    }

    private void initView() {
        Glide.get(QQMainActivity.this).clearMemory();
        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                Glide.get(QQMainActivity.this).clearDiskCache();
            }
        });

        smMain = (MySlidingMenu) findViewById(R.id.sm_main);

        initLeftView();
        initRightView();
    }

    private void initLeftView() {
        leftView = findViewById(R.id.include_menu);

        qqLevel = (QQLevelLayout) leftView.findViewById(R.id.qql_qq_level);
        ivBackground = (ImageView) leftView.findViewById(R.id.iv_menu_background);
        ivScan = (ImageView) leftView.findViewById(R.id.iv_menu_scan);
        ivMenuAvatar = (ImageView) leftView.findViewById(R.id.iv_menu_avatar);
        tvMenuName = (TextView) leftView.findViewById(R.id.tv_menu_name);
        llSign = (LinearLayout) leftView.findViewById(R.id.ll_menu_sign);
        tvSign = (TextView) leftView.findViewById(R.id.tv_menu_sign);

        ivBackground.setOnClickListener(leftClick);
        ivScan.setOnClickListener(leftClick);
        llSign.setOnClickListener(leftClick);

        tvMenuName.setText("COKU");
        qqLevel.setLevel(59);

        GlideCircleTransform transform = new GlideCircleTransform(this)
                .setBorderThickness(DisplayUtil.dip2px(this, 3))
                .setColor(255, 255, 255, 1);

        Glide.with(this).load(R.mipmap.app_icon)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .placeholder(R.mipmap.app_icon)
                .centerCrop()
                .transform(transform)
                .dontAnimate()
                .into(ivMenuAvatar);
    }

    private void initRightView() {
        rightView = findViewById(R.id.include_content);
        titleTab = (SegmentTabLayout) rightView.findViewById(R.id.stl_title_tab);
        ivMainAvatar = (ImageView) rightView.findViewById(R.id.iv_main_avatar);
        ivAdd = (ImageView) rightView.findViewById(R.id.iv_qq_add);
        rlNetworkWarm = (RelativeLayout) findViewById(R.id.rl_network_warnning);

        Glide.with(this).load(R.mipmap.app_icon)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new GlideCircleTransform(this))
                .dontAnimate()
                .into(ivMainAvatar);

        titleTab.setTabData(titles);
        titleTab.setOnTabSelectListener(tabSelect);
        ivMainAvatar.setOnClickListener(rightClick);
        ivAdd.setOnClickListener(rightClick);
        rlNetworkWarm.setOnClickListener(rightClick);

        list = new ArrayList<>();
        adapter = new QQMainAdapter(this, list);
        adapter.setOnListClickListener(listClick);

        recyclerView = (XRecyclerView) rightView.findViewById(R.id.xrv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }

            @Override
            public void onLoadMore() {
//                doLoadMore();
            }
        });

        for (int i = 0; i < SlidingMenuResUtil.QQ_AVATAR.length; i++) {
            QQMessageBean qq = new QQMessageBean();
            qq.setAvatarRes(SlidingMenuResUtil.QQ_AVATAR[i]);
            qq.setName(SlidingMenuResUtil.QQ_NAME[i]);
            qq.setContent(SlidingMenuResUtil.QQ_NAME[i]);
            qq.setTime(SlidingMenuResUtil.QQ_TIME[i]);
            list.add(qq);
        }
        adapter.notifyDataSetChanged();
    }

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            ToastUtil.toast(list.get(position).getName());
        }

        @Override
        public void onTagClick(Tag tag, int position) {

        }
    };

    private View.OnClickListener leftClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_menu_background:
                    ToastUtil.toast("背景");
                    break;
                case R.id.iv_menu_scan:
                    ToastUtil.toast("二维码");
                    break;
                case R.id.ll_menu_sign:
                    ToastUtil.toast("签名");
                    break;
            }
        }
    };

    private View.OnClickListener rightClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_main_avatar:
                    smMain.toggleMenu();
                    break;
                case R.id.rl_network_warnning:
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    break;
                case R.id.iv_qq_add:
                    ToastUtil.toast("添加");
                    break;
            }
        }
    };

    private OnTabSelectListener tabSelect = new OnTabSelectListener() {
        @Override
        public void onTabSelect(int position) {
            setTopTab(position);
        }

        @Override
        public void onTabReselect(int position) {
            // TODO: 2016/11/21 点击当前选中的tab后的操作
        }
    };

    public void setTopTab(int position) {
        if (position == 0) {
            recyclerView.setVisibility(View.VISIBLE);
            rightView.findViewById(R.id.tv_qq_phone).setVisibility(View.GONE);
        } else {
            rightView.findViewById(R.id.tv_qq_phone).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void doRefresh() {
        ThreadPoolUtil.getInstache().scheduled(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        for (int i = 0; i < SlidingMenuResUtil.QQ_AVATAR.length; i++) {
                            QQMessageBean qq = new QQMessageBean();
                            qq.setAvatarRes(SlidingMenuResUtil.QQ_AVATAR[i]);
                            qq.setName(SlidingMenuResUtil.QQ_NAME[i]);
                            qq.setContent(SlidingMenuResUtil.QQ_NAME[i]);
                            qq.setTime(SlidingMenuResUtil.QQ_TIME[i]);
                            list.add(qq);
                        }
                        adapter.notifyDataSetChanged();
                        recyclerView.refreshComplete();
                    }
                });
            }
        }, 2, TimeUnit.SECONDS);
    }

    private void doLoadMore() {

    }

    @Override
    public void onBackPressed() {
        if (smMain.isOpen()) {
            smMain.toggleMenu();
        } else {
            finish();
        }
    }
}
