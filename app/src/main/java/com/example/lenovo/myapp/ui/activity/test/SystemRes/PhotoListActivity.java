package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.cxb.tools.utils.FileUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.AlbumListBean;
import com.example.lenovo.myapp.model.testbean.PhotoBean;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.systemres.PhotoListAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.myapp.ui.activity.test.systemres.ClippingImageActivity.KEY_IMAGE_URI;

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

    private TextView tvPictureInfo;

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

        tvPictureInfo = (TextView) findViewById(R.id.tv_picture_info);
    }

    private void setData() {
        tvBack.setOnClickListener(click);
        tvCancel.setOnClickListener(click);
        tvPictureInfo.setOnClickListener(click);

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

    private void showPictureMsg() {
        AlphaAnimation mShowAction = new AlphaAnimation(0, 1);
        mShowAction.setDuration(500);
        tvPictureInfo.setAnimation(mShowAction);
        tvPictureInfo.setVisibility(View.VISIBLE);
    }

    private void hidePictureMsg() {
        AlphaAnimation mHiddenAction = new AlphaAnimation(1, 0);
        mHiddenAction.setDuration(500);
        tvPictureInfo.setAnimation(mHiddenAction);
        tvPictureInfo.setVisibility(View.GONE);
    }

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent();
            intent.setClass(PhotoListActivity.this, ClippingImageActivity.class);
            intent.putExtra(KEY_IMAGE_URI, list.get(position).getData());
            startActivity(intent);
        }

        @Override
        public void onTagClick(Tag tag, int position) {
            if (tag == Tag.LINEARLAYOUT) {
                PhotoBean pb = list.get(position);
                String content = "";
                content += "ID：" + pb.getId() + "\n";
                content += "图片路径：" + pb.getData() + "\n";
                content += "图片大小：" + FileUtil.FormetFileSize(PhotoListActivity.this, pb.getSize()) + "\n";
                content += "显示名称：" + pb.getDisplayName() + "\n";
                content += "标题：" + pb.getTitle() + "\n";
                content += "创建日期：" + pb.getDateAdded() + "\n";
                content += "最后修改日期：" + pb.getDateModified() + "\n";
                content += "MIME类型：" + pb.getType() + "\n";
                content += "图片宽度：" + pb.getWidth() + "\n";
                content += "图片高度：" + pb.getHeight() + "\n";
                content += "描述：" + pb.getDescription() + "\n";
                content += "PicasaId：" + pb.getPicasaId() + "\n";
                content += "是否私有：" + pb.getIsprivate() + "\n";
                content += "捕获图像的纬度：" + pb.getLatitude() + "\n";
                content += "捕获图像的经度：" + pb.getLongitude() + "\n";
                content += "拍摄时间（毫秒）：" + pb.getDateTaken() + "\n";
                content += "图片角度：" + pb.getOrientation() + "\n";
                content += "MiniThumbMagic：" + pb.getMiniThumbMagic() + "\n";
                content += "上级目录ID：" + pb.getBucketId() + "\n";
                content += "上级目录显示名称：" + pb.getBucketDisplayName() + "\n";
                tvPictureInfo.setText(content);
                showPictureMsg();

                Logger.d(content);
            }
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
                case R.id.tv_picture_info:
                    hidePictureMsg();
                    break;
            }
        }
    };
}
