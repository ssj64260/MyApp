package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.cxb.tools.utils.FastClick;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.TabEntity;
import com.example.lenovo.myapp.ui.base.BaseAppCompatActivity;
import com.example.lenovo.myapp.ui.fragment.DiscoveryFragment;
import com.example.lenovo.myapp.ui.fragment.HomeFragment;
import com.example.lenovo.myapp.ui.fragment.MineFragment;
import com.example.lenovo.myapp.utils.ToastMaster;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.orhanobut.logger.Logger;

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

    private Fragment[] fragmentList;

    private CommonTabLayout tabLayout;

    private final int tabCount = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_main);

        initView();
        setData();
        initFragment(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("activity_will_destory", true);
        super.onSaveInstanceState(outState);
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

    private void initFragment(Bundle savedInstanceState) {
        fragmentList = new Fragment[tabCount];
        if (savedInstanceState != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            List<Fragment> list = getSupportFragmentManager().getFragments();
            if (list != null && list.size() >= 3) {
                for (Fragment fragment : list) {
                    if (fragment instanceof HomeFragment) {
                        fragmentList[0] = fragment;
                    } else if (fragment instanceof DiscoveryFragment) {
                        fragmentList[1] = fragment;
                    } else if (fragment instanceof MineFragment) {
                        fragmentList[2] = fragment;
                    }
                }
            }
            transaction.commit();
        }

        if (fragmentList[0] == null) {
            fragmentList[0] = new HomeFragment();
        }
        if (fragmentList[1] == null) {
            fragmentList[1] = new DiscoveryFragment();
        }
        if (fragmentList[2] == null) {
            fragmentList[2] = new MineFragment();
        }

        showFragment(0);
    }

    private void showFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        List<Fragment> list = getSupportFragmentManager().getFragments();

        if (list != null) {
            String content = "";
            for (Fragment fragment : list) {
                transaction.hide(fragment);
                content += fragment.getClass().getSimpleName() + ":" + fragment.isAdded() + "\n";
            }
            Logger.d(content);
        }

        if (position < fragmentList.length) {
            Fragment fragment = fragmentList[position];

            if (!fragment.isAdded()) {
                transaction.add(R.id.fl_fragment, fragment);
            }
            transaction.show(fragment);
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (FastClick.isExitClick()) {
            finish();
        } else {
            ToastMaster.toast(getString(R.string.tosat_double_click_to_exit));
        }
    }
}
