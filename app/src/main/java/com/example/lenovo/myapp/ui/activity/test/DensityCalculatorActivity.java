package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.DensityInfo;
import com.example.lenovo.myapp.ui.adapter.DensityCalculatorAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * DP 计算器
 */

public class DensityCalculatorActivity extends BaseActivity {

    private RecyclerView rvDensityList;
    private Spinner spDensity;
    private EditText etDP;
    private EditText etPX;
    private Button btnCalculate;

    private List<DensityInfo> mDensityList;
    private DensityCalculatorAdapter mDensityAdapter;

    private final String[] densityNames = {
            "ldpi",
            "mdpi",
            "hdpi",
            "xhdpi",
            "xxhdpi",
            "xxxhdpi"
    };

    private final float[] densitys = {
            DisplayMetrics.DENSITY_LOW,
            DisplayMetrics.DENSITY_MEDIUM,
            DisplayMetrics.DENSITY_HIGH,
            DisplayMetrics.DENSITY_XHIGH,
            DisplayMetrics.DENSITY_XXHIGH,
            DisplayMetrics.DENSITY_XXXHIGH
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density_calculator);

        initView();
        setData();

    }

    private void initView() {
        rvDensityList = (RecyclerView) findViewById(R.id.rv_density_list);
        spDensity = (Spinner) findViewById(R.id.sp_density);
        etDP = (EditText) findViewById(R.id.et_dp);
        etPX = (EditText) findViewById(R.id.et_px);
        btnCalculate = (Button) findViewById(R.id.btn_calculate);
    }

    private void setData() {
        mDensityList = new ArrayList<>();
        for (int i = 0; i < densitys.length; i++) {
            mDensityList.add(new DensityInfo(densityNames[i], densitys[i]));
        }

        mDensityAdapter = new DensityCalculatorAdapter(this, mDensityList);
        rvDensityList.setLayoutManager(new LinearLayoutManager(this));
        rvDensityList.setAdapter(mDensityAdapter);
        rvDensityList.setHasFixedSize(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_density_spinner, densityNames);
        spDensity.setAdapter(adapter);

        etDP.setOnEditorActionListener(editorAction);
        etDP.setOnFocusChangeListener(focusChange);
        etPX.setOnEditorActionListener(editorAction);
        etPX.setOnFocusChangeListener(focusChange);
        btnCalculate.setOnClickListener(click);
    }

    private void doCalculate() {
        final String inputDP = etDP.getText().toString().trim();
        final String inputPX = etPX.getText().toString().trim();
        final float selectDensity = densitys[spDensity.getSelectedItemPosition()];

        for (DensityInfo density : mDensityList) {
            if (!TextUtils.isEmpty(inputDP)) {
                density.doCalculateDP(selectDensity, Float.parseFloat(inputDP));
            } else if (!TextUtils.isEmpty(inputPX)) {
                density.doCalculatePX(selectDensity, Float.parseFloat(inputPX));
            } else {
                density.doCalculateDP(selectDensity, 0);
            }
        }
        mDensityAdapter.notifyDataSetChanged();
    }

    //点击监听
    private final View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_calculate:
                    doCalculate();
                    break;
            }
        }
    };

    private final TextView.OnEditorActionListener editorAction = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doCalculate();
            }
            return false;
        }
    };

    private final View.OnFocusChangeListener focusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            switch (v.getId()) {
                case R.id.et_dp:
                case R.id.et_px:
                    etDP.setText("");
                    etPX.setText("");
                    break;
            }
        }
    };

}
