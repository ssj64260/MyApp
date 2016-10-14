package com.example.lenovo.myapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;

/**
 * 启动页
 */

public class StartupPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_page);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startNextActivity();
                    }
                });
            }
        }).start();

    }

    private void startNextActivity() {
        Intent main = new Intent();
        main.setClass(this, MainActivity.class);
        startActivity(main);

        Intent ad = new Intent();
        ad.setClass(this, AdPagesActivity.class);
        startActivity(ad);

        finish();
    }

    @Override
    public void onBackPressed() {

    }
}
