package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

/**
 * 获取系统资源
 */

public class SystemResActivity extends BaseActivity {

    private TextView tvGetPermissions;

    private TextView tvGetAlbum;
    private TextView tvGetPhoto;

    private TextView tvGetContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_res);

        initView();

        setData();

    }

    private void initView() {
        tvGetPermissions = (TextView) findViewById(R.id.tv_get_permissions);

        tvGetAlbum = (TextView) findViewById(R.id.tv_get_ablum);
        tvGetPhoto = (TextView) findViewById(R.id.tv_get_photo);
        tvGetContacts = (TextView) findViewById(R.id.tv_get_contacts);
    }

    private void setData() {
        tvGetPermissions.setOnClickListener(click);
        tvGetAlbum.setOnClickListener(click);
        tvGetPhoto.setOnClickListener(click);
        tvGetContacts.setOnClickListener(click);
    }

    private void getPermissions(){
        String appName = getString(R.string.app_name);
        permissions = new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_SMS,
                Manifest.permission.BODY_SENSORS
        };
        refuseTips = new String[]{
                String.format(getString(R.string.text_contacts_permission_message), appName),
                String.format(getString(R.string.text_call_permission_message), appName),
                String.format(getString(R.string.text_calendar_permission_message), appName),
                String.format(getString(R.string.text_camera_permission_message), appName),
                String.format(getString(R.string.text_location_permission_message), appName),
                String.format(getString(R.string.text_storage_permission_message), appName),
                String.format(getString(R.string.text_audio_permission_message), appName),
                String.format(getString(R.string.text_sms_permission_message), appName),
                String.format(getString(R.string.text_sensors_permission_message), appName)
        };
        setPermissions();
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_get_permissions:
                    getPermissions();
                    break;
                case R.id.tv_get_ablum:
                    startActivity(new Intent(SystemResActivity.this, AlbumListActivity.class));
                    break;
                case R.id.tv_get_photo:
                    startActivity(new Intent(SystemResActivity.this, SystemGetPhotoActivity.class));
                    break;
                case R.id.tv_get_contacts:
                    startActivity(new Intent(SystemResActivity.this, ContactsListActivity.class));
                    break;
            }
        }
    };

    @Override
    public void onPermissionSuccess() {
        ToastMaster.toast(getString(R.string.toast_had_got_all_permission));
    }
}
