package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cxb.tools.Glide.GlideCircleTransform;
import com.cxb.tools.SlidingMenu.MySlidingMenu;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;
import com.example.lenovo.myapp.model.MainListBean;
import com.example.lenovo.myapp.ui.adapter.MainAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧滑栏测试
 */

public class SlidingMenuTestActivity extends BaseActivity {

    private MySlidingMenu smMain;

    private ImageView ivMainAvatar;//主页头像

    private XRecyclerView recyclerView;
    private List<MainListBean> list;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu_test);

        initView();

    }

    private void initView() {
        smMain = (MySlidingMenu) findViewById(R.id.sm_main);

        ivMainAvatar = (ImageView) findViewById(R.id.iv_main_avatar);

        Glide.with(this).load(R.mipmap.app_icon)
                .transform(new GlideCircleTransform(this))
                .into(ivMainAvatar);

        ivMainAvatar.setOnClickListener(click);

        recyclerView = (XRecyclerView) findViewById(R.id.xrv_list);
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
        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.toast(list.get(position).getName());
            }
        });

        for (int i = 0; i < 10; i++) {
            list.add(new MainListBean("分类" + i / 4, "测试数据#" + i));
        }
        adapter.notifyDataSetChanged();
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_main_avatar:
                    smMain.toggleMenu();
                    break;
            }
        }
    };

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
        });
    }
}
