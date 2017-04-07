package com.example.lenovo.myapp.ui.activity.test.cameratest;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

/**
 * 相机工具
 */

public class CameraToolsActivity extends BaseActivity {

    private Button btnCamera;
    private Button btnCamera2;
    private Button btnFlashLight;

    private CameraManager mCameraManager;

    private boolean isON = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_tools);

        initView();
        setData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isON) {
            switchFlashlight();
        }
    }

    private void initView() {
        btnCamera = (Button) findViewById(R.id.btn_camera);
        btnCamera2 = (Button) findViewById(R.id.btn_camera2);
        btnFlashLight = (Button) findViewById(R.id.btn_flashlight);
    }

    private void setData() {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        btnCamera.setOnClickListener(click);
        btnCamera2.setOnClickListener(click);
        btnFlashLight.setOnClickListener(click);
    }

    private void switchFlashlight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isON = !isON;
            btnFlashLight.setText(isON ? getString(R.string.btn_flashlight_on) : getString(R.string.btn_flashlight_off));

            try {
                mCameraManager.setTorchMode("0", isON);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            ToastMaster.toast("只支持6.0及以上的系统");
        }
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_camera:
                    startActivity(new Intent(CameraToolsActivity.this, CameraTestActivity.class));
                    break;
                case R.id.btn_camera2:
                    startActivity(new Intent(CameraToolsActivity.this, Camera2TestActivity.class));
                    break;
                case R.id.btn_flashlight:
                    switchFlashlight();
                    break;
            }
        }
    };

}
