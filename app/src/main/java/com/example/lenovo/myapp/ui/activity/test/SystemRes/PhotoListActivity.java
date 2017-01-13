package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.AlbumListBean;
import com.example.lenovo.myapp.model.testbean.PhotoBean;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.systemres.PhotoListAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 相册对应的相片列表
 */

public class PhotoListActivity extends BaseActivity {

    public static final String KEY_ALBUM = "key_album";

    private TextView tvBack;
    private TextView tvTitle;
    private TextView tvCancel;

    private RecyclerView rvPhoto;
    private List<PhotoBean> list;
    private PhotoListAdapter mAdapter;

    private AlbumListBean album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        initView();
        setData();
    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);

        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);

    }

    private void setData() {
        tvBack.setOnClickListener(click);
        tvCancel.setOnClickListener(click);

        list = new ArrayList<>();
        mAdapter = new PhotoListAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvPhoto.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        rvPhoto.setAdapter(mAdapter);

        album = (AlbumListBean) getIntent().getSerializableExtra(KEY_ALBUM);
        if (album != null) {
            tvTitle.setText(album.getName());
            List<PhotoBean> temp = album.getPhotos();

            if (temp != null) {
                list.clear();
                list.addAll(temp);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            ToastUtil.toast(list.get(position).getPath());
        }

        @Override
        public void onTagClick(Tag tag, int position) {

        }
    };

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_back:
                    finish();
                    break;
                case R.id.tv_cancel:
                    Intent intent = new Intent();
                    intent.setClass(PhotoListActivity.this, SystemResActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                    break;
            }
        }
    };
}
