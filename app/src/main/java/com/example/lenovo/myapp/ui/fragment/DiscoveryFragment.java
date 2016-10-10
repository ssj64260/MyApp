package com.example.lenovo.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxb.tools.NewsTab.HorizontalTabListScrollView;
import com.cxb.tools.NewsTab.NewsTab;
import com.cxb.tools.NewsTab.NewsTabResoureUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近
 */

public class DiscoveryFragment extends Fragment {

    private HorizontalTabListScrollView svNewsTabs;
    private List<NewsTab> list;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_discovery, null);

            initView();
        }

        return view;
    }

    private void initView() {
        svNewsTabs = (HorizontalTabListScrollView) view.findViewById(R.id.htlsv_list);
        svNewsTabs.setOnItemSelectedListener(new HorizontalTabListScrollView.OnItemSelectedListener() {
            @Override
            public void onItemClick(View v, int postion) {
                ToastUtil.toast(list.get(postion).getName());
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < NewsTabResoureUtil.characteristic.length; i++) {
            list.add(new NewsTab(String.valueOf(i), NewsTabResoureUtil.characteristic[i]));
        }
        svNewsTabs.addTabList(list);
    }
}
