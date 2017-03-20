package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.app.Activity;
import android.content.ClipData;
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
import com.cxb.tools.utils.SDCardUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.dialog.ChooseDialog;
import com.example.lenovo.myapp.utils.ToastMaster;
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
        setImageFromIntent(getIntent());
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
                setImageFromIntent(data);
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                if (photoUri.exists() && resultCode == Activity.RESULT_OK) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(photoUri);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);

                    Glide.with(SystemGetPhotoActivity.this)
                            .load(photoUri.getAbsolutePath())
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.drawable.ic_no_image_circle)
                            .error(R.drawable.ic_no_image_circle)
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

    private void setImageFromIntent(Intent intent) {
        if (intent != null) {
            String type = intent.getType();

            if ("text/plain".equals(type)) {
                ToastMaster.toast(getString(R.string.toast_do_not_accept_text_data));
            } else {
                Uri uri = null;
                if (intent.getData() != null) {
                    uri = intent.getData();
                } else if (intent.getClipData() != null) {
                    ClipData clipData = intent.getClipData();
                    int itemCount = clipData.getItemCount();
                    if (itemCount > 0) {
                        ClipData.Item item = clipData.getItemAt(0);
                        uri = item.getUri();
                    }
                } else {
                    uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                }

                if (uri != null) {
                    String url = uri.getPath();
                    Logger.d(url);

                    if (url.endsWith(".gif")) {
                        Glide.with(SystemGetPhotoActivity.this)
                                .load(uri)
                                .asGif()
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .dontAnimate()
                                .into(ivPhoto);
                    } else {
                        Glide.with(SystemGetPhotoActivity.this)
                                .load(uri)
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .dontAnimate()
                                .into(ivPhoto);
                    }
                }
            }
        }
    }

    private void showChooseDialog() {
        chooseDialog = new ChooseDialog(this);
        chooseDialog.setOnFirstButtonListener(getString(R.string.btn_choose_from_mobile), new ChooseDialog.OnFirstButtonListener() {
            @Override
            public void OnFirstButtonListener(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, REQUESTCODE_PICK);
            }
        });
        chooseDialog.setOnSecondButtonListener(getString(R.string.btn_choose_from_take_photo), new ChooseDialog.OnSecondButtonListener() {
            @Override
            public void OnSecondButtonListener(View v) {
                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takeIntent.resolveActivity(getPackageManager()) != null) {
                    photoUri = new File(PhotoDirectory, System.currentTimeMillis() + ".jpg");
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoUri));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                } else {
                    ToastMaster.toast(getString(R.string.toast_not_activity_response));
                }
            }
        });
        chooseDialog.setOnCancelListener(getString(R.string.btn_do_not_choose), new ChooseDialog.OnCancelListener() {
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
