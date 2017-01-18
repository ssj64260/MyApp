package com.example.lenovo.myapp.ui.activity.test.systemres;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

/**
 * 系统联系人列表
 */

public class ContactsListActivity extends BaseActivity {

    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        initView();
        setData();

    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tv_back);
    }

    private void setData() {
        tvBack.setOnClickListener(click);

        String appName = getString(R.string.app_name);
        permissions = new String[]{
                Manifest.permission.READ_CONTACTS,
        };
        refuseTips = new String[]{
                String.format("在设置-应用-%1$s-权限中开启通讯录权限，以正常使用获取联系人信息功能", appName),
        };
        setPermissions();
    }

    @Override
    protected void doSomeThing() {
        getContacts();
    }

    private void getContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String content = "";
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    content += cursor.getString(i) + "\n";
//                    content += cursor.getColumnName(i) + "\n";
                }
                Logger.d(content);
            }
        }
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_back:
                    finish();
                    break;
            }
        }
    };

}
