package com.example.lenovo.myapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.activity.MainActivity;
import com.example.lenovo.myapp.ui.activity.QQMainActivity;
import com.example.lenovo.myapp.ui.activity.test.MyToolsActivity;

/**
 * 我的
 */

public class MineFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, null);
            initView();
        }

        return view;
    }

    private void initView() {
        view.findViewById(R.id.tv_qq_layout).setOnClickListener(click);
        view.findViewById(R.id.tv_md_layout).setOnClickListener(click);
        view.findViewById(R.id.tv_my_tools).setOnClickListener(click);
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_md_layout:
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    break;
                case R.id.tv_qq_layout:
                    startActivity(new Intent(getActivity(), QQMainActivity.class));
                    break;
                case R.id.tv_my_tools:
                    startActivity(new Intent(getActivity(), MyToolsActivity.class));
                    break;
            }
        }
    };
}
