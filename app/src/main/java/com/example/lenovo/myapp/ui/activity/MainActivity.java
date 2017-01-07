package com.example.lenovo.myapp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cxb.tools.Glide.GlideCircleTransform;
import com.cxb.tools.utils.DataCleanManager;
import com.cxb.tools.utils.GetFileSizeUtil;
import com.cxb.tools.utils.NetworkUtil;
import com.cxb.tools.utils.SDCardUtil;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.dialog.TipsActionDialog;
import com.example.lenovo.myapp.model.MainListBean;
import com.example.lenovo.myapp.ui.activity.test.AnimationTestActivity;
import com.example.lenovo.myapp.ui.activity.test.DateTimeTestActivity;
import com.example.lenovo.myapp.ui.activity.test.DialogTestActivity;
import com.example.lenovo.myapp.ui.activity.test.OkhttpTestActivity;
import com.example.lenovo.myapp.ui.activity.test.ThreadPoolTestActivity;
import com.example.lenovo.myapp.ui.activity.test.AppInfoTestActivity;
import com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestTestActivity;
import com.example.lenovo.myapp.ui.adapter.MainAdapter;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.base.BaseAppCompatActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity {

    private ImageView ivAvatar;//侧滑栏头像
    private TextView tvUsername;//侧滑栏用户名
    private TextView tvEmail;//侧滑栏用户邮箱

    private DrawerLayout drawer;
    private ImageView ivMainAvatar;//主页头像

    private XRecyclerView recyclerView;
    private List<MainListBean> list;
    private MainAdapter adapter;

    private RelativeLayout rlNetworkWarm;

    private NavigationView navigationView;//左侧滑动菜单

    BroadcastReceiver checkNetwork = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int networkStatus = NetworkUtil.checkNetWorkType(MainActivity.this);
            int apnType = NetworkUtil.checkAPNType(MainActivity.this);

            switch (networkStatus) {
                case NetworkUtil.NETWORK_NONE:
                    rlNetworkWarm.setVisibility(View.VISIBLE);
                    break;
                case NetworkUtil.NETWORK_MOBILE:
                    if (apnType == NetworkUtil.NETWORK_TYPE_CMNET) {
                        ToastUtil.toast("已连接CMNET移动网络");
                    } else {
                        ToastUtil.toast("已连接CMWAP移动网络");
                    }
                    rlNetworkWarm.setVisibility(View.GONE);
                    break;
                case NetworkUtil.NETWORK_WIFI:
                    ToastUtil.toast("已连接WIFI网络，可以正常使用");
                    rlNetworkWarm.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setData();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkNetwork, intentFilter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCacheSize();
    }

    private void initView() {
        rlNetworkWarm = (RelativeLayout) findViewById(R.id.rl_network_warnning);
        rlNetworkWarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivMainAvatar = (ImageView) findViewById(R.id.iv_main_avatar);
        recyclerView = (XRecyclerView) findViewById(R.id.xrv_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "收到新的推送", Snackbar.LENGTH_LONG)
                        .setAction("忽略", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.toast("忽略了该推送");
                            }
                        })
                        .show();
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        navigationView.setNavigationItemSelectedListener(selectedListener);

        ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        tvUsername = (TextView) headerView.findViewById(R.id.tv_username);
        tvEmail = (TextView) headerView.findViewById(R.id.tv_email);

    }

    private void setData() {
        Glide.get(MainActivity.this).clearMemory();
//        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
//            @Override
//            public void run() {
//                Glide.get(MainActivity.this).clearDiskCache();
//            }
//        });

        GlideCircleTransform transform = new GlideCircleTransform(this)
                .setBorderThickness(10)
                .setColor(255, 255, 255, 1);

        Glide.with(this).load(R.mipmap.app_icon)
                .transform(transform)
//                .placeholder(R.mipmap.app_icon)
                .dontAnimate()
                .into(ivAvatar);

        Glide.with(this).load(R.mipmap.app_icon)
                .transform(new GlideCircleTransform(this))
                .dontAnimate()
                .into(ivMainAvatar);

        ivMainAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        tvUsername.setText("COKU");
        tvEmail.setText("799536767@qq.com");

        list = new ArrayList<>();
        adapter = new MainAdapter(this, list);
        adapter.setOnListClickListener(listClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                doRefreshLoading(true);
            }

            @Override
            public void onLoadMore() {
                doRefreshLoading(false);
            }
        });

        for (int i = 0; i < 10; i++) {
            list.add(new MainListBean("分类" + i / 4, "测试数据#" + i));
        }
        adapter.notifyDataSetChanged();
    }

    private void doRefreshLoading(final boolean isRefresh) {
        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (isRefresh) {
                    list.clear();
                }

                for (int i = 0; i < 10; i++) {
                    if (isRefresh) {
                        list.add(new MainListBean("刷新分类" + i / 4, "刷新数据#" + i));
                    } else {
                        list.add(new MainListBean("加载分类" + list.size() / 4, "加载数据#" + list.size()));
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.refreshComplete();

                        if (list.size() > 39) {
                            recyclerView.setNoMore(true);
                        }
                    }
                });
            }
        });
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

    private NavigationView.OnNavigationItemSelectedListener selectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, IntroductionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_camera:
                    ToastUtil.toast("打开相机");
                    break;
                case R.id.nav_gallery:
                    Intent okhttpIntent = new Intent();
                    okhttpIntent.setClass(MainActivity.this, OkhttpTestActivity.class);
                    startActivity(okhttpIntent);
                    break;
                case R.id.nav_slideshow:
                    Intent animIntent = new Intent();
                    animIntent.setClass(MainActivity.this, AnimationTestActivity.class);
                    startActivity(animIntent);
                    break;
                case R.id.nav_dialog_test:
                    Intent dialogIntent = new Intent();
                    dialogIntent.setClass(MainActivity.this, DialogTestActivity.class);
                    startActivity(dialogIntent);
                    break;
                case R.id.nav_thread_pool_test:
                    Intent threadIntent = new Intent();
                    threadIntent.setClass(MainActivity.this, ThreadPoolTestActivity.class);
                    startActivity(threadIntent);
                    break;
                case R.id.nav_datetime_test:
                    Intent datetimeIntent = new Intent();
                    datetimeIntent.setClass(MainActivity.this, DateTimeTestActivity.class);
                    startActivity(datetimeIntent);
                    break;
                case R.id.nav_list_nest_test:
                    Intent nestIntent = new Intent();
                    nestIntent.setClass(MainActivity.this, ListNestTestActivity.class);
                    startActivity(nestIntent);
                    break;
                case R.id.nav_app_info_test:
                    Intent appIntent = new Intent();
                    appIntent.setClass(MainActivity.this, AppInfoTestActivity.class);
                    startActivity(appIntent);
                    break;
                case R.id.nav_manage:
                    showTipsActionDialog();
                    break;
            }

            return true;
        }
    };

    //获取缓存大小并设置到menu里
    private void getCacheSize() {
        long cacheSize = 0;
        try {
            String cacheDir = SDCardUtil.getCacheDir(this);
            String externalCacheDir = SDCardUtil.getExternalCacheDir(this);

            cacheSize += GetFileSizeUtil.getFileSize(new File(cacheDir));
            cacheSize += GetFileSizeUtil.getFileSize(new File(externalCacheDir));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String cache = GetFileSizeUtil.FormetFileSize(this, cacheSize);

        if (navigationView != null) {
            navigationView.getMenu().findItem(R.id.nav_manage).setTitle("清理缓存：" + cache);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(checkNetwork);
        super.onDestroy();
    }

    ///////////////////////////////////////////////////////////////////////////
    // show dialog method
    ///////////////////////////////////////////////////////////////////////////
    private void showTipsActionDialog() {
        TipsActionDialog dialog = new TipsActionDialog(this);
        dialog.setTitleText("清理缓存？");
        dialog.setConfirmText("清理");
        dialog.setCancelText("取消");
        dialog.setOnConfirmListener(new TipsActionDialog.OnConfirmListener() {
            @Override
            public void OnConfirmListener(View v) {
                DataCleanManager.cleanInternalCache(MainActivity.this);
                DataCleanManager.cleanExternalCache(MainActivity.this);

                getCacheSize();
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // 参考代码
    ///////////////////////////////////////////////////////////////////////////
    /*private void initToolBar(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("空白页");
//        toolbar.setLogo(R.mipmap.ic_launcher);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_settings) {
                    ToastUtil.toast("settings");
                }
                return true;
            }
        });

        int iconWidth = DisplayUtil.getInstance().dip2px(40);
        int borderWidth = DisplayUtil.getInstance().dip2px(5);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(iconWidth, iconWidth));
        Glide.with(this)
                .load(R.mipmap.app_icon)
                .transform(new GlideCircleTransform(this).setBorderThickness(borderWidth).setColor(255, 255, 255, 1))
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        toolbar.addView(imageView, 0);
//        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ToastUtil.toast(getString(R.string.navigation_drawer_close));
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ToastUtil.toast(getString(R.string.navigation_drawer_open));
            }
        };
        toggle.setDrawerIndicatorEnabled(false);
//        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }*/
}
