package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.AlbumListBean;
import com.example.lenovo.myapp.model.testbean.PhotoBean;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.systemres.AlbumListAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.dialog.DefaultProgressDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Images.Media.PICASA_ID;
import static android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.Media.BUCKET_ID;
import static android.provider.MediaStore.Images.Media.DATA;
import static android.provider.MediaStore.Images.Media.DATE_ADDED;
import static android.provider.MediaStore.Images.Media.DATE_MODIFIED;
import static android.provider.MediaStore.Images.Media.DATE_TAKEN;
import static android.provider.MediaStore.Images.Media.DESCRIPTION;
import static android.provider.MediaStore.Images.Media.DISPLAY_NAME;
import static android.provider.MediaStore.Images.Media.IS_PRIVATE;
import static android.provider.MediaStore.Images.Media.LATITUDE;
import static android.provider.MediaStore.Images.Media.LONGITUDE;
import static android.provider.MediaStore.Images.Media.MIME_TYPE;
import static android.provider.MediaStore.Images.Media.MINI_THUMB_MAGIC;
import static android.provider.MediaStore.Images.Media.ORIENTATION;
import static android.provider.MediaStore.Images.Media.SIZE;
import static android.provider.MediaStore.Images.Media.TITLE;
import static android.provider.MediaStore.Images.Media.WIDTH;
import static android.provider.MediaStore.Images.Media._ID;
import static android.provider.MediaStore.Images.Media.HEIGHT;
import static com.example.lenovo.myapp.ui.activity.test.systemres.PhotoListActivity.KEY_ALBUM;

/**
 * 相册列表
 */

public class AlbumListActivity extends BaseActivity {

    private TextView tvCancel;
    private RecyclerView rvAlbumList;

    private List<AlbumListBean> list;
    private AlbumListAdapter mAdapter;

    private DefaultProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        initView();
        setData();

    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null) {
            progressDialog.dismissDialog();
        }
        super.onDestroy();
    }

    private void initView() {
        progressDialog = new DefaultProgressDialog(this);
        progressDialog.setMessage(getString(R.string.text_loading));

        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        rvAlbumList = (RecyclerView) findViewById(R.id.rv_album_list);
    }

    private void setData() {
        tvCancel.setOnClickListener(click);

        String appName = getString(R.string.app_name);
        permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        refuseTips = new String[]{
                String.format(getString(R.string.text_storage_permission_message), appName),
        };
        setPermissions();
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

    @Override
    public void onPermissionSuccess() {
        progressDialog.showDialog();

        list = new ArrayList<>();
        mAdapter = new AlbumListAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);
        rvAlbumList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                ContentResolver cr = getContentResolver();  //获取ContentResolver
                Cursor mCursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                if (mCursor != null) {
                    mCursor.moveToLast();
                    do {
//                        String temp = "";
//                        for (int i = 0; i < mCursor.getColumnCount(); i++) {
//                            temp += mCursor.getColumnName(i) + ":\t" + mCursor.getString(mCursor.getColumnIndex(mCursor.getColumnName(i))) + "\n";
//                        }
//                        Logger.d(temp);

                        PhotoBean photo = new PhotoBean();
                        photo.setId(mCursor.getLong(mCursor.getColumnIndex(_ID)));

                        photo.setBucketDisplayName(mCursor.getString(mCursor.getColumnIndex(BUCKET_DISPLAY_NAME)));
                        photo.setBucketId(mCursor.getString(mCursor.getColumnIndex(BUCKET_ID)));
                        photo.setDateTaken(mCursor.getLong(mCursor.getColumnIndex(DATE_TAKEN)));
                        photo.setDescription(mCursor.getString(mCursor.getColumnIndex(DESCRIPTION)));
                        photo.setIsprivate(mCursor.getInt(mCursor.getColumnIndex(IS_PRIVATE)));
                        photo.setLatitude(mCursor.getDouble(mCursor.getColumnIndex(LATITUDE)));
                        photo.setLongitude(mCursor.getDouble(mCursor.getColumnIndex(LONGITUDE)));
                        photo.setMiniThumbMagic(mCursor.getLong(mCursor.getColumnIndex(MINI_THUMB_MAGIC)));
                        photo.setOrientation(mCursor.getInt(mCursor.getColumnIndex(ORIENTATION)));
                        photo.setPicasaId(mCursor.getString(mCursor.getColumnIndex(PICASA_ID)));

                        photo.setData(mCursor.getString(mCursor.getColumnIndex(DATA)));
                        photo.setDateAdded(mCursor.getLong(mCursor.getColumnIndex(DATE_ADDED)));
                        photo.setDateModified(mCursor.getLong(mCursor.getColumnIndex(DATE_MODIFIED)));
                        photo.setDisplayName(mCursor.getString(mCursor.getColumnIndex(DISPLAY_NAME)));
                        photo.setHeight(mCursor.getInt(mCursor.getColumnIndex(HEIGHT)));
                        photo.setType(mCursor.getString(mCursor.getColumnIndex(MIME_TYPE)));
                        photo.setSize(mCursor.getLong(mCursor.getColumnIndex(SIZE)));
                        photo.setTitle(mCursor.getString(mCursor.getColumnIndex(TITLE)));
                        photo.setWidth(mCursor.getInt(mCursor.getColumnIndex(WIDTH)));

                        boolean isNew = true;

                        if (list.size() > 0) {
                            for (AlbumListBean al : list) {
                                if (al.getPath().equals(new File(photo.getData()).getParent())) {
                                    al.getPhotos().add(photo);
                                    isNew = false;
                                }
                            }
                        }

                        if (isNew) {
                            AlbumListBean al = new AlbumListBean();
                            al.setName(photo.getBucketDisplayName());
                            al.setPath(new File(photo.getData()).getParent());
                            al.setIcon(photo.getData());
                            List<PhotoBean> pList = new ArrayList<>();
                            pList.add(photo);
                            al.setPhotos(pList);
                            list.add(al);
                        }
                    } while (mCursor.moveToPrevious());

                    if (list.size() > 0) {
                        AlbumListBean al = new AlbumListBean();
                        al.setName("最近相片");
                        al.setIcon(list.get(0).getPhotos().get(0).getData());

                        List<PhotoBean> pList = new ArrayList<>();
                        for (AlbumListBean alb : list) {
                            pList.addAll(alb.getPhotos());
                        }
                        al.setPhotos(pList);
                        list.add(0, al);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rvAlbumList.setAdapter(mAdapter);
                            progressDialog.dismissDialog();
                        }
                    });
                }
            }
        });
    }
}
