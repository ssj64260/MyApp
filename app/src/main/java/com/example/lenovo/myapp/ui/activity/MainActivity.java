package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseAppCompatActivity;
import com.example.lenovo.myapp.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.dialog.InputContentDialog;
import com.example.lenovo.myapp.dialog.TipsActionDialog;
import com.example.lenovo.myapp.model.MainListBean;
import com.example.lenovo.myapp.ui.adapter.MainAdapter;
import com.example.lenovo.myapp.utils.FastClick;
import com.example.lenovo.myapp.utils.StringCheck;
import com.example.lenovo.myapp.utils.ToastUtil;
import com.example.lenovo.myapp.widgets.Glide.GlideCircleTransform;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DefaultProgressDialog progressDialog;

    private ImageView ivAvatar;//侧滑栏头像
    private TextView tvUsername;//侧滑栏用户名
    private TextView tvEmail;//侧滑栏用户邮箱

    private DrawerLayout drawer;
    private ImageView ivMainAvatar;//主页头像

    private XRecyclerView recyclerView;
    private List<MainListBean> list;
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setData();
    }

    private void initView() {
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        tvUsername = (TextView) headerView.findViewById(R.id.tv_username);
        tvEmail = (TextView) headerView.findViewById(R.id.tv_email);

        progressDialog = new DefaultProgressDialog(this);
    }

    private void setData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(MainActivity.this).clearDiskCache();
            }
        }).start();

        GlideCircleTransform transform = new GlideCircleTransform(this)
                .setBorderThickness(10)
                .setColor(255, 255, 255, 1);

        Glide.with(this).load(R.mipmap.app_icon)
                .transform(transform)
//                .placeholder(R.mipmap.app_icon)
                .into(ivAvatar);

        Glide.with(this).load(R.mipmap.app_icon)
                .transform(new GlideCircleTransform(this))
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

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

        list = new ArrayList<>();
        adapter = new MainAdapter(this, list);
        recyclerView.setAdapter(adapter);


        for (int i = 0; i < 10; i++) {
            list.add(new MainListBean("分类" + i / 4, "测试数据NO." + i));
        }
        adapter.notifyDataSetChanged();
    }

    private void doRefreshLoading(final boolean isRefresh) {
        new Thread(new Runnable() {
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
                        list.add(new MainListBean("刷新分类" + i / 4, "刷新测试数据NO." + i));
                    } else {
                        list.add(new MainListBean("加载分类" + list.size() / 4, "加载测试数据NO." + list.size()));
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        recyclerView.stopAll();

                        if (list.size() > 39) {
//                            recyclerView.noMoreLoading();
                            recyclerView.setLoadingMoreEnabled(false);
                        }

                        if (isRefresh) {
                            recyclerView.setLoadingMoreEnabled(true);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (FastClick.isExitClick()) {
                finish();
            } else {
                ToastUtil.toast("再次点击退出程序");
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_camera:
                ToastUtil.toast("打开相机");
                break;
            case R.id.nav_gallery:
                ToastUtil.toast("打开相册");
                break;
            case R.id.nav_slideshow:
                ToastUtil.toast("打开幻灯片");
                break;
            case R.id.nav_manage:
                showTipsActionDialog();
                break;
            case R.id.nav_share:
                showProgressDialog();
                break;
            case R.id.nav_send:
                showInputContentDialog();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////
    // show dialog method
    ///////////////////////////////////////////////////////////////////////////
    private void showTipsActionDialog() {
        TipsActionDialog dialog = new TipsActionDialog(this);
        dialog.setTitleText("是否打开设置");
        dialog.setConfirmText("打开");
        dialog.setCancelText("否");
    }

    private void showProgressDialog() {
        progressDialog.setMessage("分享中...");
        progressDialog.showDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismissDialog();
                    }
                });
            }
        }).start();
    }

    private void showInputContentDialog() {
        InputContentDialog dialog = new InputContentDialog(this);
        dialog.setCanDismissByBackPressed(false);
        dialog.setTitleText("请填写发送的内容");
        dialog.setConfirmText("发送");
        dialog.setCancelText("关闭");
        dialog.setOnConfirmListener(new InputContentDialog.OnConfirmListener() {
            @Override
            public void OnConfirmListener(View v, String content) {
                if (StringCheck.isEmpty(content)) {
                    ToastUtil.toast("内容不能为空");
                } else {
                    ToastUtil.toast(content);
                }
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
