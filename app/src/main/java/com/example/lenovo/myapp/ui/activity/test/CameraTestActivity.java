package com.example.lenovo.myapp.ui.activity.test;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cxb.tools.camera.CameraTextureView;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.activity.test.systemres.AlbumListActivity;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

/**
 * \相机工具
 */

public class CameraTestActivity extends BaseActivity {

    private ImageView ivChangeCamera;
    private ImageView ivHdr;
    private ImageView ivFlash;
    private ImageView ivSetting;
    private ImageView ivVideo;
    private ImageView ivTakePic;
    private ImageView ivAlbum;

    private CameraTextureView csPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        initView();
        setData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        csPreview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        csPreview.onPause();
    }

    private void initView() {
        ivChangeCamera = (ImageView) findViewById(R.id.iv_change_camera);
        ivHdr = (ImageView) findViewById(R.id.iv_hdr);
        ivFlash = (ImageView) findViewById(R.id.iv_flash);
        ivSetting = (ImageView) findViewById(R.id.iv_camera_setting);
        ivVideo = (ImageView) findViewById(R.id.iv_video);
        ivTakePic = (ImageView) findViewById(R.id.iv_take_picture);
        ivAlbum = (ImageView) findViewById(R.id.iv_album);

        csPreview = (CameraTextureView) findViewById(R.id.cs_preview);
    }

    private void setData() {
        ivChangeCamera.setOnClickListener(click);
        ivHdr.setOnClickListener(click);
        ivFlash.setOnClickListener(click);
        ivSetting.setOnClickListener(click);
        ivVideo.setOnClickListener(click);
        ivTakePic.setOnClickListener(click);
        ivAlbum.setOnClickListener(click);
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_change_camera:
                    csPreview.changCameraFacing();
                    break;
                case R.id.iv_hdr:
                    ToastMaster.toast("HDR");
                    break;
                case R.id.iv_flash:
                    String flashMode = csPreview.changeFlashMode();

                    if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
                        ivFlash.setImageResource(R.drawable.ic_flash_off);
                    } else if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)) {
                        ivFlash.setImageResource(R.drawable.ic_flash_auto);
                    } else if (Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {
                        ivFlash.setImageResource(R.drawable.ic_flash_on);
                    }

                    break;
                case R.id.iv_camera_setting:
                    ToastMaster.toast("相机设置");
                    break;
                case R.id.iv_video:
                    ToastMaster.toast("录像");
                    break;
                case R.id.iv_take_picture:
                    csPreview.takePhoto();
                    break;
                case R.id.iv_album:
                    startActivity(new Intent(CameraTestActivity.this, AlbumListActivity.class));
                    break;
            }
        }
    };

}
