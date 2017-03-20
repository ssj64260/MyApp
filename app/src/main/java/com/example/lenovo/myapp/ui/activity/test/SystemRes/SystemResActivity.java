package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;

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

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_get_permissions:
                    startActivity(new Intent(SystemResActivity.this, GetPermissionActivity.class));
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

}
