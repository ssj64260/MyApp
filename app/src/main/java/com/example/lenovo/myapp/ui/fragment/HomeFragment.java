package com.example.lenovo.myapp.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxb.tools.maintab.MainTab;
import com.cxb.tools.maintab.MainTabListLayout;
import com.cxb.tools.maintab.MainTabResoureUtil;
import com.cxb.tools.utils.FastClick;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseFragment;
import com.example.lenovo.myapp.utils.CustomToast;
import com.example.lenovo.myapp.utils.ToastMaster;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

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

                    LinearLayout linearLayout = new LinearLayout(getActivity());
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    linearLayout.setGravity(Gravity.CENTER);

                    ImageView imageView = new ImageView(getActivity());
                    imageView.setImageResource(tabList.get(position).getLogoResource());

                    TextView textView = new TextView(getActivity());
                    textView.setTextColor(Color.parseColor("#ffffff"));
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(tabList.get(position).getName());

                    linearLayout.addView(imageView);
                    linearLayout.addView(textView);

                    CustomToast.toastView(linearLayout);
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
                ToastMaster.toast("二维码");
                break;
            case R.id.iv_message:
                ToastMaster.toast("消息中心");
                break;
            case R.id.ll_search_bar:
                ToastMaster.toast("搜索");
                break;
        }
    }
}
