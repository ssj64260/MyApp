package com.example.lenovo.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxb.tools.NewsTab.HorizontalTabListScrollView;
import com.cxb.tools.NewsTab.NewsTab;
import com.cxb.tools.utils.AssetsUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PropertyBean;
import com.example.lenovo.myapp.ui.adapter.MyPagerAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近
 */

public class DiscoveryFragment extends Fragment {

    private HorizontalTabListScrollView svNewsTabs;
    private List<NewsTab> list;
    private List<PropertyBean> propertyList;

    private ViewPager mViewPager;
    private List<Fragment> fragmentList = new ArrayList<>();

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_discovery, null);

            initView();
            setData();
        }

        return view;
    }

    private void initView() {
        mViewPager = (ViewPager) view.findViewById(R.id.vp_fragment);

        svNewsTabs = (HorizontalTabListScrollView) view.findViewById(R.id.htlsv_list);
        svNewsTabs.setOnItemSelectedListener(new HorizontalTabListScrollView.OnItemSelectedListener() {
            @Override
            public void onItemClick(View v, int postion) {
                mViewPager.setCurrentItem(postion);
            }
        });

    }

    private void setData() {
//        String json = AssetsUtil.getAssetsTxtByName(getActivity(), "property");
//        Type newsType = new TypeToken<List<NewsTab>>() {}.getType();

        list = new ArrayList<>();
        list.addAll((List<NewsTab>) AssetsUtil.getObjectByName(getActivity(), "property", new TypeToken<List<NewsTab>>() {
        }.getType()));
        propertyList = new ArrayList<>();
        propertyList.addAll((List<PropertyBean>) AssetsUtil.getObjectByName(getActivity(), "property", new TypeToken<List<PropertyBean>>() {
        }.getType()));

        svNewsTabs.addTabList(list);

        for (int i = 0; i < propertyList.size(); i++) {
            fragmentList.add(DiscoveryPageFragment.newInstance(propertyList.get(i).getEn_name()));
        }

        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                svNewsTabs.changeTabByPostion(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
