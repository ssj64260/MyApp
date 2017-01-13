package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.AlbumListBean;
import com.example.lenovo.myapp.model.testbean.PhotoBean;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.systemres.AlbumListAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.myapp.ui.activity.test.systemres.PhotoListActivity.KEY_ALBUM;

/**
 * 相册列表
 */

public class AlbumListActivity extends BaseActivity {

    private TextView tvCancel;
    private RecyclerView rvAlbumList;

    private List<AlbumListBean> list;
    private AlbumListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        initView();
        setData();
        getImage();

    }

    private void initView() {
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        rvAlbumList = (RecyclerView) findViewById(R.id.rv_album_list);
    }

    private void setData() {
        tvCancel.setOnClickListener(click);

        list = new ArrayList<>();
        mAdapter = new AlbumListAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvAlbumList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAlbumList.setAdapter(mAdapter);
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
                String temp = "";
                for (int i = 0; i < mCursor.getColumnCount(); i++) {
                    temp += mCursor.getColumnName(i) + ":\t" + mCursor.getString(mCursor.getColumnIndex(mCursor.getColumnName(i))) + "\n";
                }
                Logger.d(temp);

                PhotoBean photo = new PhotoBean();
                photo.setTitle(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.TITLE)));
                photo.setFileName(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                photo.setAlbumName(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)));
                photo.setPath(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                photo.setSize(mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE)));
                photo.setDateTaken(mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
                photo.setType(mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)));
                photo.setWidth(mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.WIDTH)));
                photo.setHeight(mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)));

                boolean isNew = true;

                if (list.size() > 0) {
                    for (AlbumListBean al : list) {
                        if (al.getPath().equals(new File(photo.getPath()).getParent())) {
                            al.getPhotos().add(photo);
                            isNew = false;
                        }
                    }
                }

                if (isNew) {
                    AlbumListBean al = new AlbumListBean();
                    al.setName(photo.getAlbumName());
                    al.setPath(new File(photo.getPath()).getParent());
                    al.setIcon(photo.getPath());
                    List<PhotoBean> pList = new ArrayList<>();
                    pList.add(photo);
                    al.setPhotos(pList);
                    list.add(al);
                }
            } while (mCursor.moveToPrevious());

            if (list.size() > 0) {
                AlbumListBean al = new AlbumListBean();
                al.setName("最近相片");
                al.setIcon(list.get(0).getPhotos().get(0).getPath());

                List<PhotoBean> pList = new ArrayList<>();
                for (AlbumListBean alb : list) {
                    pList.addAll(alb.getPhotos());
                }
                al.setPhotos(pList);
                list.add(0, al);

                Intent intent = new Intent();
                intent.setClass(AlbumListActivity.this, PhotoListActivity.class);
                intent.putExtra(KEY_ALBUM, list.get(0));
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent();
            intent.setClass(AlbumListActivity.this, PhotoListActivity.class);
            intent.putExtra(KEY_ALBUM, list.get(position));
            startActivity(intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
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
                case R.id.tv_cancel:
                    finish();
                    break;
            }
        }
    };

}
