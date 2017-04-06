package com.example.lenovo.myapp.ui.activity.test.cameratest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;

/**
 * 相机工具
 */

public class CameraToolsActivity extends BaseActivity {

    private Button btnCamera;
    private Button btnCamera2;
    private Button btnFlashLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_tools);

        initView();
        setData();

    }

    private void initView() {
        btnCamera = (Button) findViewById(R.id.btn_camera);
        btnCamera2 = (Button) findViewById(R.id.btn_camera2);
        btnFlashLight = (Button) findViewById(R.id.btn_flashlight);
    }

    private void setData() {
        btnCamera.setOnClickListener(click);
        btnCamera2.setOnClickListener(click);
        btnFlashLight.setOnClickListener(click);
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

                    break;
            }
        }
    };

}
