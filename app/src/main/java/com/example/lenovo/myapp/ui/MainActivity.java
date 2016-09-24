package com.example.lenovo.myapp.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseAppCompatActivity;
import com.example.lenovo.myapp.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.dialog.InputContentDialog;
import com.example.lenovo.myapp.dialog.TipsActionDialog;
import com.example.lenovo.myapp.utils.FastClick;
import com.example.lenovo.myapp.utils.StringCheck;
import com.example.lenovo.myapp.utils.ToastUtil;
import com.example.lenovo.myapp.widgets.Glide.GlideCircleTransform;

public class MainActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DefaultProgressDialog progressDialog;

    private ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.inflateMenu(R.menu.activity_main_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);

        progressDialog = new DefaultProgressDialog(this);
    }

    private void setData() {

        GlideCircleTransform transform = new GlideCircleTransform(this)
                .setBorderThickness(10)
                .setColor(255, 255, 255, 1);
        Glide.with(this).load(R.mipmap.app_icon)
                .transform(transform)
//                .placeholder(R.mipmap.app_icon)
                .into(ivAvatar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            ToastUtil.toast("settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
}
