package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.adapter.AlbumPhotoAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 相册
 */

public class AlbumActivity extends BaseActivity {

    public static final String KEY_TITLE = "key_title";

    private TextView tvBack;
    private TextView tvTitle;
    private TextView tvCancel;

    private RecyclerView rvPhoto;
    private List<String> list;
    private AlbumPhotoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        initView();
        setData();
        getImage();

    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);

        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);

    }

    private void setData() {
        String title = getIntent().getStringExtra(KEY_TITLE);
        if (!StringCheck.isEmpty(title)) {
            tvTitle.setText(title);
        }

        tvBack.setOnClickListener(click);
        tvCancel.setOnClickListener(click);

        list = new ArrayList<>();
        mAdapter = new AlbumPhotoAdapter(this, list);
        rvPhoto.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        rvPhoto.setAdapter(mAdapter);
    }

    private void getImage() {
        ContentResolver cr = getContentResolver();  //获取ContentResolver
        Cursor mCursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
        if (mCursor != null) {
            mCursor.moveToLast();
            do {
                list.add(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));

                String temp = "";
                for (int i = 0; i < mCursor.getColumnCount(); i++) {
                    temp += mCursor.getColumnName(i) + ":\t" + mCursor.getString(mCursor.getColumnIndex(mCursor.getColumnName(i))) + "\n";
                }
                Logger.d(temp);
            } while (mCursor.moveToPrevious());

            mAdapter.notifyDataSetChanged();
        }
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_back:

                    break;
                case R.id.tv_cancel:
                    finish();
                    break;
            }
        }
    };
}
