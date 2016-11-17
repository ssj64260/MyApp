package com.example.lenovo.myapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.activity.MainActivity;
import com.example.lenovo.myapp.ui.activity.QQMainActivity;

/**
 * 我的
 */

public class MineFragment extends Fragment {

    private TextView tvTools;

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
        tvTools = (TextView) view.findViewById(R.id.tv_tools);
        tvTools.setOnClickListener(click);

        view.findViewById(R.id.tv_sliding_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), QQMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_tools:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
