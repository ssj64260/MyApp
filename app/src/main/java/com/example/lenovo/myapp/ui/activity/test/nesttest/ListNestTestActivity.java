package com.example.lenovo.myapp.ui.activity.test.nesttest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.GRID_TO_GRID;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.GRID_TO_LIST;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.GRID_TO_RECYCLER;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.KEY_NEST_TYPE;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.LIST_TO_GRID;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.LIST_TO_LIST;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.LIST_TO_RECYCLER;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.RECYCLER_TO_GRID;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.RECYCLER_TO_LIST;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.RECYCLER_TO_RECYCLER;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.SCROLL_TO_GRID;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.SCROLL_TO_LIST;
import static com.example.lenovo.myapp.ui.activity.test.nesttest.ListNestDatailActivity.SCROLL_TO_RECYCLER;

/**
 * 列表嵌套
 */

public class ListNestTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nest_test);

        initView();

    }

    private void initView() {
        findViewById(R.id.btn_list_list).setOnClickListener(btnClick);
        findViewById(R.id.btn_list_grid).setOnClickListener(btnClick);
        findViewById(R.id.btn_list_recycler).setOnClickListener(btnClick);
        findViewById(R.id.btn_recycler_list).setOnClickListener(btnClick);
        findViewById(R.id.btn_recycler_grid).setOnClickListener(btnClick);
        findViewById(R.id.btn_recycler_recycler).setOnClickListener(btnClick);
        findViewById(R.id.btn_grid_list).setOnClickListener(btnClick);
        findViewById(R.id.btn_grid_grid).setOnClickListener(btnClick);
        findViewById(R.id.btn_grid_recycler).setOnClickListener(btnClick);
        findViewById(R.id.btn_scroll_list).setOnClickListener(btnClick);
        findViewById(R.id.btn_scroll_grid).setOnClickListener(btnClick);
        findViewById(R.id.btn_scroll_recycler).setOnClickListener(btnClick);
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ListNestTestActivity.this, ListNestDatailActivity.class);
            switch (v.getId()) {
                case R.id.btn_list_list:
                    intent.putExtra(KEY_NEST_TYPE, LIST_TO_LIST);
                    break;
                case R.id.btn_list_grid:
                    intent.putExtra(KEY_NEST_TYPE, LIST_TO_GRID);
                    break;
                case R.id.btn_list_recycler:
                    intent.putExtra(KEY_NEST_TYPE, LIST_TO_RECYCLER);
                    break;
                case R.id.btn_recycler_list:
                    intent.putExtra(KEY_NEST_TYPE, RECYCLER_TO_LIST);
                    break;
                case R.id.btn_recycler_grid:
                    intent.putExtra(KEY_NEST_TYPE, RECYCLER_TO_GRID);
                    break;
                case R.id.btn_recycler_recycler:
                    intent.putExtra(KEY_NEST_TYPE, RECYCLER_TO_RECYCLER);
                    break;
                case R.id.btn_grid_list:
                    intent.putExtra(KEY_NEST_TYPE, GRID_TO_LIST);
                    break;
                case R.id.btn_grid_grid:
                    intent.putExtra(KEY_NEST_TYPE, GRID_TO_GRID);
                    break;
                case R.id.btn_grid_recycler:
                    intent.putExtra(KEY_NEST_TYPE, GRID_TO_RECYCLER);
                    break;
                case R.id.btn_scroll_list:
                    intent.putExtra(KEY_NEST_TYPE, SCROLL_TO_LIST);
                    break;
                case R.id.btn_scroll_grid:
                    intent.putExtra(KEY_NEST_TYPE, SCROLL_TO_GRID);
                    break;
                case R.id.btn_scroll_recycler:
                    intent.putExtra(KEY_NEST_TYPE, SCROLL_TO_RECYCLER);
                    break;
            }
            startActivity(intent);
        }
    };
}
