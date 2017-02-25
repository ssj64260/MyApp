package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.cxb.tools.utils.FastClick;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.TabEntity;
import com.example.lenovo.myapp.ui.base.BaseAppCompatActivity;
import com.example.lenovo.myapp.ui.fragment.DiscoveryFragment;
import com.example.lenovo.myapp.ui.fragment.HomeFragment;
import com.example.lenovo.myapp.ui.fragment.MineFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 口袋妖怪资讯主页
 */

public class PokemonMainActivity extends BaseAppCompatActivity {

    private final int[] iconSelect = {
            R.drawable.tab_home_select,
            R.drawable.tab_discovery_select,
            R.drawable.tab_me_select
    };
    private final int[] iconUnselect = {
            R.drawable.tab_home_unselect,
            R.drawable.tab_discovery_unselect,
            R.drawable.tab_me_unselect
    };

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> fragmentList;

    private CommonTabLayout tabLayout;

    private Fragment curFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_main);

        initView();
        setData();
        initFragment();

    }

    private void initView() {
        String[] titles = {
                getString(R.string.btn_pokemon_home),
                getString(R.string.btn_pokemon_pokedex),
                getString(R.string.btn_pokemon_mine)
        };

        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], iconSelect[i], iconUnselect[i]));
        }

        tabLayout = (CommonTabLayout) findViewById(R.id.com_tablayout);

    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new DiscoveryFragment());
        fragmentList.add(new MineFragment());

        showFragment(0);
    }

    private void showFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (curFragment != null) {
            transaction.hide(curFragment);
        }

        curFragment = getSupportFragmentManager().findFragmentByTag(fragmentList.get(position).getClass().getName());
        if (curFragment == null) {
            curFragment = fragmentList.get(position);
        }

        if (!curFragment.isAdded()) {
            transaction.add(R.id.fl_fragment, curFragment);
        } else {
            transaction.show(curFragment);
        }
        transaction.commit();
    }

    private void setData() {
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showFragment(position);

                tabLayout.hideMsg(position);
            }

            @Override
            public void onTabReselect(int position) {
                // TODO: 2016/10/9 设置重复点击事件
            }
        });

        //三位数
        tabLayout.showMsg(1, 100);
        tabLayout.setMsgMargin(1, -5, 5);

        //设置未读消息红点
        tabLayout.showDot(2);

    }

    @Override
    public void onBackPressed() {
        if (FastClick.isExitClick()) {
            finish();
        } else {
            ToastUtil.toast(getString(R.string.tosat_double_click_to_exit));
        }
    }
}
