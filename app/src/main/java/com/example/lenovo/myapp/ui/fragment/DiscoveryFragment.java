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
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.dialog.DefaultProgressDialog;
import com.example.lenovo.myapp.model.PropertyBean;
import com.example.lenovo.myapp.ui.adapter.MyPagerAdapter;
import com.cxb.tools.CustomViewpager.CustomDepthPageTransformer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    private DefaultProgressDialog progressDialog;

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
        progressDialog = new DefaultProgressDialog(getActivity());

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
        progressDialog.setMessage("加载中...");
        progressDialog.showDialog();

        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                list = new ArrayList<>();
                propertyList = new ArrayList<>();

                String json = AssetsUtil.getAssetsTxtByName(getActivity(), "property");
                if (!StringCheck.isEmpty(json)) {
                    Type newsType = new TypeToken<List<NewsTab>>() {
                    }.getType();
                    Type propertyType = new TypeToken<List<PropertyBean>>() {
                    }.getType();
                    Gson gson = new Gson();

                    List<NewsTab> newsTemp = gson.fromJson(json, newsType);
                    List<PropertyBean> propertyTemp = gson.fromJson(json, propertyType);
                    list.addAll(newsTemp);
                    propertyList.addAll(propertyTemp);
                }

                for (int i = 0; i < propertyList.size(); i++) {
                    fragmentList.add(DiscoveryPageFragment.newInstance(propertyList.get(i).getEn_name()));
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        svNewsTabs.addTabList(list);

                        mViewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList));
                        mViewPager.setPageTransformer(true, new CustomDepthPageTransformer());
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

                        progressDialog.dismissDialog();
                    }
                });
            }
        });
    }

}
