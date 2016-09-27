package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxb.tools.MainTab.MainTab;
import com.cxb.tools.MainTab.MainTabListLayout;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 头部功能按钮测试页面
 */

public class ToolsTabActivity extends BaseActivity implements View.OnClickListener {

    private final int[] icon = {
            R.mipmap.ic_abra,
            R.mipmap.ic_ursaring,
            R.mipmap.ic_magikarp,
            R.mipmap.ic_murkrow,
            R.mipmap.ic_cubone,
            R.mipmap.ic_lopunny,
            R.mipmap.ic_jolteon,
            R.mipmap.ic_flareon,
            R.mipmap.ic_eevee,
            R.mipmap.ic_entei,
            R.mipmap.ic_gyarados,
            R.mipmap.ic_isshu6,
            R.mipmap.ic_lucario,
            R.mipmap.ic_duskull,
            R.mipmap.ic_koffing,
            R.mipmap.ic_diglett,
            R.mipmap.ic_ditto,
            R.mipmap.ic_gengar,
            R.mipmap.ic_isshu12,
            R.mipmap.ic_marowak,
            R.mipmap.ic_scizor,
            R.mipmap.ic_shuckle,
            R.mipmap.ic_staraptor,
            R.mipmap.ic_weavile,
            R.mipmap.ic_zubat
    };

    private MainTabListLayout mainTabListLayout;
    private List<MainTab> tabList;
    private ImageView ivScan;
    private ImageView ivMessage;
    private LinearLayout llSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_tab);

        initView();
    }

    private void initView() {
        ivScan = (ImageView) findViewById(R.id.iv_scan);
        ivMessage = (ImageView) findViewById(R.id.iv_message);
        llSearchBar = (LinearLayout) findViewById(R.id.ll_search_bar);

        ivScan.setOnClickListener(this);
        ivMessage.setOnClickListener(this);
        llSearchBar.setOnClickListener(this);

        mainTabListLayout = (MainTabListLayout) findViewById(R.id.mtll_list);
        mainTabListLayout.setOnItemSelectedListener(new MainTabListLayout.OnItemSelectedListener() {
            @Override
            public void onItemClick(View v, int position) {
                ToastUtil.toast(tabList.get(position).getName());
            }
        });

        tabList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tabList.add(new MainTab(String.valueOf(i), icon[i], "宝可梦" + i));
        }

        mainTabListLayout.setSpanCount(5);
        mainTabListLayout.setList(tabList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
