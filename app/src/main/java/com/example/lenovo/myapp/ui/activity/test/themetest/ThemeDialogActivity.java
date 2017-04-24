package com.example.lenovo.myapp.ui.activity.test.themetest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.adapter.MyToolsAdapter;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

import java.util.ArrayList;
import java.util.List;

import static android.R.style.Theme_DeviceDefault_Dialog;
import static android.R.style.Theme_DeviceDefault_Dialog_Alert;
import static android.R.style.Theme_DeviceDefault_Dialog_MinWidth;
import static android.R.style.Theme_DeviceDefault_Dialog_NoActionBar;
import static android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth;
import static android.R.style.Theme_DeviceDefault_Light_Dialog;
import static android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar;
import static android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
import static android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth;
import static android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar;
import static android.R.style.Theme_Dialog;
import static android.R.style.Theme_Holo_Dialog;
import static android.R.style.Theme_Holo_Dialog_MinWidth;
import static android.R.style.Theme_Holo_Dialog_NoActionBar;
import static android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth;
import static android.R.style.Theme_Holo_Light_Dialog;
import static android.R.style.Theme_Holo_Light_Dialog_MinWidth;
import static android.R.style.Theme_Holo_Light_Dialog_NoActionBar;
import static android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth;
import static android.R.style.Theme_Material_Dialog;
import static android.R.style.Theme_Material_Dialog_Alert;
import static android.R.style.Theme_Material_Dialog_MinWidth;
import static android.R.style.Theme_Material_Dialog_NoActionBar;
import static android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth;
import static android.R.style.Theme_Material_Light_Dialog;
import static android.R.style.Theme_Material_Light_Dialog_Alert;
import static android.R.style.Theme_Material_Light_Dialog_MinWidth;
import static android.R.style.Theme_Material_Light_Dialog_NoActionBar;
import static android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth;

/**
 * 对话框主题
 */

public class ThemeDialogActivity extends BaseActivity {

    public static final String THEME_TYPE = "theme_type";
    public static final String DIALOG_TYPE = "dialog_type";

    public static final int DeviceDefault = 0;
    public static final int DeviceDefault_Light = 1;
    public static final int Holo = 2;
    public static final int Holo_Light = 3;
    public static final int Material = 4;
    public static final int Material_Light = 5;

    private RecyclerView rvTheme;
    private List<String> list;
    private MyToolsAdapter mAdapter;

    private int currentThemeType;
    private int dialogType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_detail);

        initView();
        setData();

    }

    @Override
    public void setTheme(int resid) {
        currentThemeType = getIntent().getIntExtra(THEME_TYPE, Theme_Dialog);
        super.setTheme(currentThemeType);
    }

    private void initView() {
        rvTheme = (RecyclerView) findViewById(R.id.rv_theme);
    }

    private void setData() {
        list = new ArrayList<>();

        dialogType = getIntent().getIntExtra(DIALOG_TYPE, -1);

        if (dialogType == DeviceDefault) {
            list.add(getString(R.string.btn_dialog_theme_2));
            list.add(getString(R.string.btn_dialog_theme_3));
            list.add(getString(R.string.btn_dialog_theme_4));
            list.add(getString(R.string.btn_dialog_theme_5));
        } else if (dialogType == DeviceDefault_Light) {
            list.add(getString(R.string.btn_dialog_theme_7));
            list.add(getString(R.string.btn_dialog_theme_8));
            list.add(getString(R.string.btn_dialog_theme_9));
            list.add(getString(R.string.btn_dialog_theme_10));
        } else if (dialogType == Holo) {
            list.add(getString(R.string.btn_dialog_theme_12));
            list.add(getString(R.string.btn_dialog_theme_13));
            list.add(getString(R.string.btn_dialog_theme_14));
        } else if (dialogType == Holo_Light) {
            list.add(getString(R.string.btn_dialog_theme_16));
            list.add(getString(R.string.btn_dialog_theme_17));
            list.add(getString(R.string.btn_dialog_theme_18));
        } else if (dialogType == Material) {
            list.add(getString(R.string.btn_dialog_theme_20));
            list.add(getString(R.string.btn_dialog_theme_21));
            list.add(getString(R.string.btn_dialog_theme_22));
            list.add(getString(R.string.btn_dialog_theme_23));
        } else if (dialogType == Material_Light) {
            list.add(getString(R.string.btn_dialog_theme_25));
            list.add(getString(R.string.btn_dialog_theme_26));
            list.add(getString(R.string.btn_dialog_theme_27));
            list.add(getString(R.string.btn_dialog_theme_28));
        } else {
            list.add(getString(R.string.btn_dialog_theme_1));
            list.add(getString(R.string.btn_dialog_theme_6));
            list.add(getString(R.string.btn_dialog_theme_11));
            list.add(getString(R.string.btn_dialog_theme_15));
            list.add(getString(R.string.btn_dialog_theme_19));
            list.add(getString(R.string.btn_dialog_theme_24));
        }

        mAdapter = new MyToolsAdapter(this, list);
        mAdapter.setOnListClickListener(listClick);

        rvTheme.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvTheme.setAdapter(mAdapter);
    }

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {

        }

        @Override
        public void onTagClick(int tag, int position) {
            if (tag == TEXTVIEW) {
                Intent intent = new Intent();
                intent.setClass(ThemeDialogActivity.this, ThemeDialogActivity.class);
                switch (dialogType) {
                    case DeviceDefault:
                        intent.putExtra(DIALOG_TYPE, DeviceDefault);
                        if (0 == position) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                                intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Dialog_Alert);
                            } else {
                                ToastMaster.toast("系统版本低于5.1，不能使用AlertDialog");
                            }
                        } else if (1 == position) {
                            intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Dialog_MinWidth);
                        } else if (2 == position) {
                            intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Dialog_NoActionBar);
                        } else if (3 == position) {
                            intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
                        }
                        break;
                    case DeviceDefault_Light:
                        intent.putExtra(DIALOG_TYPE, DeviceDefault_Light);
                        if (0 == position) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                                intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Light_Dialog_Alert);
                            } else {
                                ToastMaster.toast("系统版本低于5.1，不能使用AlertDialog");
                            }
                        } else if (1 == position) {
                            intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Light_Dialog_MinWidth);
                        } else if (2 == position) {
                            intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Light_Dialog_NoActionBar);
                        } else if (3 == position) {
                            intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Light_DialogWhenLarge_NoActionBar);
                        }
                        break;
                    case Holo:
                        intent.putExtra(DIALOG_TYPE, Holo);
                        if (0 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Holo_Dialog_MinWidth);
                        } else if (1 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Holo_Dialog_NoActionBar);
                        } else if (2 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Holo_Dialog_NoActionBar_MinWidth);
                        }
                        break;
                    case Holo_Light:
                        intent.putExtra(DIALOG_TYPE, Holo_Light);
                        if (0 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Holo_Light_Dialog_MinWidth);
                        } else if (1 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Holo_Light_Dialog_NoActionBar);
                        } else if (2 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                        }
                        break;
                    case Material:
                        intent.putExtra(DIALOG_TYPE, Material);
                        if (0 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Material_Dialog_Alert);
                        } else if (1 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Material_Dialog_MinWidth);
                        } else if (2 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Material_Dialog_NoActionBar);
                        } else if (3 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Material_Dialog_NoActionBar_MinWidth);
                        }
                        break;
                    case Material_Light:
                        intent.putExtra(DIALOG_TYPE, Material_Light);
                        if (0 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Material_Light_Dialog_Alert);
                        } else if (1 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Material_Light_Dialog_MinWidth);
                        } else if (2 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Material_Light_Dialog_NoActionBar);
                        } else if (3 == position) {
                            intent.putExtra(THEME_TYPE, Theme_Material_Light_Dialog_NoActionBar_MinWidth);
                        }
                        break;
                    default:
                        if (0 == position) {
                            intent.putExtra(DIALOG_TYPE, DeviceDefault);
                            intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Dialog);
                        } else if (1 == position) {
                            intent.putExtra(DIALOG_TYPE, DeviceDefault_Light);
                            intent.putExtra(THEME_TYPE, Theme_DeviceDefault_Light_Dialog);
                        } else if (2 == position) {
                            intent.putExtra(DIALOG_TYPE, Holo);
                            intent.putExtra(THEME_TYPE, Theme_Holo_Dialog);
                        } else if (3 == position) {
                            intent.putExtra(DIALOG_TYPE, Holo_Light);
                            intent.putExtra(THEME_TYPE, Theme_Holo_Light_Dialog);
                        } else if (4 == position) {
                            intent.putExtra(DIALOG_TYPE, Material);
                            intent.putExtra(THEME_TYPE, Theme_Material_Dialog);
                        } else if (5 == position) {
                            intent.putExtra(DIALOG_TYPE, Material_Light);
                            intent.putExtra(THEME_TYPE, Theme_Material_Light_Dialog);
                        }
                        break;
                }
                startActivity(intent);
                finish();
            }
        }
    };

}
