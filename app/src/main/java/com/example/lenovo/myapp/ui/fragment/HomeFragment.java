package com.example.lenovo.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxb.tools.MainTab.MainTab;
import com.cxb.tools.MainTab.MainTabListLayout;
import com.cxb.tools.MainTab.MainTabResoureUtil;
import com.cxb.tools.utils.FastClick;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private MainTabListLayout mainTabListLayout;
    private List<MainTab> tabList;
    private ImageView ivScan;
    private ImageView ivMessage;
    private LinearLayout llSearchBar;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, null);

            initView();
        }
        return view;
    }

    private void initView() {
        ivScan = (ImageView) view.findViewById(R.id.iv_scan);
        ivMessage = (ImageView) view.findViewById(R.id.iv_message);
        llSearchBar = (LinearLayout) view.findViewById(R.id.ll_search_bar);

        ivScan.setOnClickListener(this);
        ivMessage.setOnClickListener(this);
        llSearchBar.setOnClickListener(this);

        mainTabListLayout = (MainTabListLayout) view.findViewById(R.id.mtll_list);
        mainTabListLayout.setOnItemSelectedListener(new MainTabListLayout.OnItemSelectedListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (!FastClick.isFastClick()) {
                    ToastUtil.toast(tabList.get(position).getName());
                }
            }
        });

        tabList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tabList.add(new MainTab(String.valueOf(i), MainTabResoureUtil.icon[i], MainTabResoureUtil.name[i]));
        }

        mainTabListLayout.setList(tabList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:
                ToastUtil.toast("二维码");
                break;
            case R.id.iv_message:
                ToastUtil.toast("消息中心");
                break;
            case R.id.ll_search_bar:
                ToastUtil.toast("搜索");
                break;
        }
    }
}
