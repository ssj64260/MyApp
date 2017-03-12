package com.example.lenovo.myapp.ui.activity.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cxb.tools.utils.SDCardUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 请求外部APP文件
 */

public class IntentFileActivity extends BaseActivity {

    private Button btnRequestFile;
    private Button btnResponseFile;
    private TextView tvResponse;

    private ParcelFileDescriptor mInputPFD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_file);

        initView();
        setData();
        showResponseButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri returnUri = data.getData();
            try {
                mInputPFD = getContentResolver().openFileDescriptor(returnUri, "r");
                if (mInputPFD != null) {
                    FileDescriptor fd = mInputPFD.getFileDescriptor();
                    FileInputStream inputStream = new FileInputStream(fd);

                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    byte[] bufffer = new byte[inputStream.available()];
                    int len = 0;
                    while ((len = inputStream.read(bufffer)) != -1) {
                        bout.write(bufffer, 0, len);
                    }
                    byte[] content = bout.toByteArray();
                    String str = new String(content);

                    bout.close();
                    inputStream.close();

                    tvResponse.setText(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        btnRequestFile = (Button) findViewById(R.id.btn_request_file);
        btnResponseFile = (Button) findViewById(R.id.btn_response_file);
        tvResponse = (TextView) findViewById(R.id.tv_response);
    }

    private void setData() {
        btnRequestFile.setOnClickListener(click);
        btnResponseFile.setOnClickListener(click);
    }

    private void showResponseButton() {
        Intent request = getIntent();
        if (request != null) {
            String action = request.getAction();
            if (Intent.ACTION_PICK.equals(action)) {
                btnResponseFile.setVisibility(View.VISIBLE);
                btnRequestFile.setVisibility(View.GONE);
            }
        }
    }

    private void requestFile() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("text/plain");
        startActivityForResult(intent, 0);
    }

    private void responseFile() {
        File txtFile = new File(SDCardUtil.getFilesDir(this) + File.separator + "txt", "property.txt");
        Intent result = new Intent("com.example.lenovo.myapp.fileprovider");
        Uri fileUri = FileProvider.getUriForFile(this, "com.example.lenovo.myapp.fileprovider", txtFile);
        if (fileUri != null) {
            result.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            result.setDataAndType(fileUri, getContentResolver().getType(fileUri));
            setResult(RESULT_OK, result);
            finish();
        }
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_request_file:
                    requestFile();
                    break;
                case R.id.btn_response_file:
                    responseFile();
                    break;
            }
        }
    };

}
