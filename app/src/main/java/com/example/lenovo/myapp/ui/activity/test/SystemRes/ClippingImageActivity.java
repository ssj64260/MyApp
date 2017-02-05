package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cxb.tools.utils.DataCleanManager;
import com.cxb.tools.utils.ImageUtil;
import com.cxb.tools.utils.SDCardUtil;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

/**
 * 裁剪图片
 */

public class ClippingImageActivity extends BaseActivity {

    public static final String KEY_IMAGE_URI = "key_image_uri";

    private File PhotoDirectory;//图片路径
    private File photoUri;//图片完整uri

    private TextView tvConfirm;
    private TextView tvCancel;
    private CropImageView civClipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipping_image);

        initView();
        setData();

    }

    @Override
    protected void onDestroy() {
        DataCleanManager.deleteFilesByDirectory(PhotoDirectory);
        super.onDestroy();
    }

    private void initView() {
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        civClipper = (CropImageView) findViewById(R.id.civ_clipper);
    }

    private void setData() {
        PhotoDirectory = new File(SDCardUtil.getAutoFilesPath(this));

        tvConfirm.setOnClickListener(click);
        tvCancel.setOnClickListener(click);

        String uri = getIntent().getStringExtra(KEY_IMAGE_URI);
        if (!StringCheck.isEmpty(uri)) {
            if (!uri.contains("file://")) {
                uri = "file://" + uri;
            }
            civClipper.setImageUriAsync(Uri.parse(uri));
        }
    }

    private void uploadAvatar() {
        photoUri = new File(PhotoDirectory, System.currentTimeMillis() + ".jpg");
        ImageUtil.saveBitmapToJpg(civClipper.getCroppedImage(),
                PhotoDirectory.getAbsolutePath(),
                photoUri.getName(),
                30);
        civClipper.setImageBitmap(civClipper.getCroppedImage());
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_confirm:
                    uploadAvatar();
                    break;
                case R.id.tv_cancel:
                    finish();
                    break;
            }
        }
    };

}
