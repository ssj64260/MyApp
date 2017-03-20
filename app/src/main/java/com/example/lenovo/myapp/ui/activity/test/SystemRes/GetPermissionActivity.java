package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.adapter.MyToolsAdapter;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获取系统权限
 */

public class GetPermissionActivity extends BaseActivity {

    private List<String> list;
    private MyToolsAdapter mAdapter;
    private RecyclerView rvPermission;

    private String[] permissionNames;
    private String[] totalPermissions;
    private String[] totalRefuseTips;

    private int currentPermission = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_permission);

        initView();
        setData();

    }

    private void initView() {
        rvPermission = (RecyclerView) findViewById(R.id.rv_permission);
    }

    private void setData() {
        String appName = getString(R.string.app_name);
        totalPermissions = new String[]{
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
        totalRefuseTips = new String[]{
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

        list = new ArrayList<>();
        mAdapter = new MyToolsAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvPermission.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvPermission.setAdapter(mAdapter);

        permissionNames = new String[]{
                getString(R.string.btn_permission_all),
                getString(R.string.btn_permission_contacts),
                getString(R.string.btn_permission_call),
                getString(R.string.btn_permission_calendar),
                getString(R.string.btn_permission_camera),
                getString(R.string.btn_permission_location),
                getString(R.string.btn_permission_storage),
                getString(R.string.btn_permission_audio),
                getString(R.string.btn_permission_sms),
                getString(R.string.btn_permission_sensors)
        };

        list.clear();
        Collections.addAll(list, permissionNames);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPermissionSuccess() {
        ToastMaster.toast(String.format(getString(R.string.toast_had_got_this_permission), permissionNames[currentPermission]));
    }

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {

        }

        @Override
        public void onTagClick(@ItemView int tag, int position) {
            if (tag == TEXTVIEW) {
                currentPermission = position;
                switch (currentPermission) {
                    case 0:
                        permissions = totalPermissions;
                        refuseTips = totalRefuseTips;
                        break;
                    default:
                        int permissionPosition = currentPermission - 1;
                        if (permissionPosition < totalPermissions.length) {
                            permissions = new String[]{totalPermissions[permissionPosition]};
                            refuseTips = new String[]{totalRefuseTips[permissionPosition]};
                        }
                        break;
                }
                setPermissions();
            }
        }
    };

}
