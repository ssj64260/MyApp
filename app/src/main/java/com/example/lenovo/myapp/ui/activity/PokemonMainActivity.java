package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseAppCompatActivity;
import com.example.lenovo.myapp.model.TabEntity;
import com.example.lenovo.myapp.ui.adapter.MyPagerAdapter;
import com.example.lenovo.myapp.ui.fragment.HomeFragment;
import com.example.lenovo.myapp.ui.fragment.MineFragment;
import com.example.lenovo.myapp.ui.fragment.NearbyFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.DefaultIconUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 口袋妖怪资讯主页
 */

public class PokemonMainActivity extends BaseAppCompatActivity {

    private String[] titles = {"首页", "附近", "我的"};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    private CommonTabLayout tabLayout;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_main);

        initView();
        setData();

    }

    private void initView() {
        for (int i = 0; i < titles.length; i++) {
            mTabEntities.add(new TabEntity(titles[i], DefaultIconUtil.iconSelect[i], DefaultIconUtil.iconUnselect[i]));
        }

        tabLayout = (CommonTabLayout) findViewById(R.id.com_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.vp_fragment);

    }

    private void setData() {
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);

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

        mFragments.add(new HomeFragment());
        mFragments.add(new NearbyFragment());
        mFragments.add(new MineFragment());

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                tabLayout.hideMsg(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

//    @Override
//    public void onBackPressed() {
//        if (FastClick.isExitClick()) {
//            finish();
//        } else {
//            ToastUtil.toast("再次点击退出程序");
//        }
//    }
}
