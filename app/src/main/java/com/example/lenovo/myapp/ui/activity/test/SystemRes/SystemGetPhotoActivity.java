package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cxb.tools.utils.DataCleanManager;
import com.cxb.tools.utils.FileUtil;
import com.cxb.tools.utils.SDCardUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.dialog.ChooseDialog;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * 从系统相册，或系统相机获取图片
 */

public class SystemGetPhotoActivity extends BaseActivity {

    private static final int REQUESTCODE_PICK = 0;// 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;// 相机拍照标记

    private File PhotoDirectory;//图片路径
    private File photoUri;//图片完整uri

    private ImageView ivPhoto;
    private TextView tvChangePhoto;

    private ChooseDialog chooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_get_photo);

        initView();

        setData();

    }

    @Override
    protected void onDestroy() {
        if (chooseDialog != null) {
            chooseDialog.dismiss();
        }

        DataCleanManager.deleteFilesByDirectory(PhotoDirectory);

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                if (data != null && data.getData() != null) {
                    String url = data.getData().toString();
                    if (url.contains("content:")) {
                        url = FileUtil.getContentImage(url, this);
                    }

                    Glide.with(SystemGetPhotoActivity.this)
                            .load(url)
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.mipmap.ic_no_image_circle)
                            .error(R.mipmap.ic_no_image_circle)
                            .dontAnimate()
                            .into(ivPhoto);

                    Logger.d(url);
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                if (photoUri.exists() && resultCode == Activity.RESULT_OK) {
                    Glide.with(SystemGetPhotoActivity.this)
                            .load(photoUri.getAbsolutePath())
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.mipmap.ic_no_image_circle)
                            .error(R.mipmap.ic_no_image_circle)
                            .dontAnimate()
                            .into(ivPhoto);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        ivPhoto = (ImageView) findViewById(R.id.iv_photo);
        tvChangePhoto = (TextView) findViewById(R.id.tv_change_photo);
    }

    private void setData() {
        PhotoDirectory = new File(SDCardUtil.getAutoFilesPath(this));

        tvChangePhoto.setOnClickListener(click);
    }

    private void showChooseDialog() {
        chooseDialog = new ChooseDialog(this);
        chooseDialog.setOnFirstButtonListener("从手机相册选择", new ChooseDialog.OnFirstButtonListener() {
            @Override
            public void OnFirstButtonListener(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, REQUESTCODE_PICK);
            }
        });
        chooseDialog.setOnSecondButtonListener("拍照", new ChooseDialog.OnSecondButtonListener() {
            @Override
            public void OnSecondButtonListener(View v) {
                photoUri = new File(PhotoDirectory, System.currentTimeMillis() + ".jpg");
                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoUri));
                startActivityForResult(takeIntent, REQUESTCODE_TAKE);
            }
        });
        chooseDialog.setOnCancelListener("取消", new ChooseDialog.OnCancelListener() {
            @Override
            public void OnCancelListener(View v) {

            }
        });
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_change_photo:
                    showChooseDialog();
                    break;
            }
        }
    };

}
