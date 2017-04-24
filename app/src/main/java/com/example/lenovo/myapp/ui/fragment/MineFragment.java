package com.example.lenovo.myapp.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.activity.MainActivity;
import com.example.lenovo.myapp.ui.activity.QQMainActivity;
import com.example.lenovo.myapp.ui.activity.test.MyToolsActivity;
import com.example.lenovo.myapp.ui.base.BaseFragment;

/**
 * 我的
 */

public class MineFragment extends BaseFragment {

    private View view;
    private Intent toQQIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, container, false);
            initView();
            setData();
        }

        return view;
    }

    private void initView() {
        view.findViewById(R.id.tv_qq_layout).setOnClickListener(click);
        view.findViewById(R.id.tv_md_layout).setOnClickListener(click);
        view.findViewById(R.id.tv_my_tools).setOnClickListener(click);
    }

    private void setData() {
        String packName = "com.cxb.qq";

        PackageManager packageManager = getActivity().getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView tvToQQ = (TextView) view.findViewById(R.id.tv_qq_layout);
        if (packageInfo != null) {
            toQQIntent = packageManager.getLaunchIntentForPackage(packName);
            tvToQQ.setText(getString(R.string.btn_imitate_qq_interface_outside));
        } else {
            toQQIntent = new Intent(getActivity(), QQMainActivity.class);
            tvToQQ.setText(getString(R.string.btn_imitate_qq_interface_inside));
        }
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_md_layout:
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    break;
                case R.id.tv_qq_layout:
                    startActivity(toQQIntent);
                    break;
                case R.id.tv_my_tools:
                    startActivity(new Intent(getActivity(), MyToolsActivity.class));
                    break;
            }
        }
    };
}
