package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseAppCompatActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * dpi计算器
 */

public class DpiCalculatorActivity extends BaseAppCompatActivity {

    private EditText etWidth;
    private EditText etHeight;
    private TextView tvTips;
    private EditText etInch;
    private EditText etDpi;
    private TextView tvDoCalculate;

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_do_calculate:
                    final String inch = etInch.getText().toString();
                    final String dpi = etDpi.getText().toString();
                    if (!TextUtils.isEmpty(inch)) {
                        inchToDpi(inch);
                    } else if (!TextUtils.isEmpty(dpi)) {
                        dpiToInch(dpi);
                    }
                    break;
            }
        }
    };

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            setEditTextStatus();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnFocusChangeListener mFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                switch (v.getId()) {
                    case R.id.et_inch:
                        etDpi.setText("");
                        break;
                    case R.id.et_dpi:
                        etInch.setText("");
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpi_calcuator);

        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        etWidth.removeTextChangedListener(mTextWatcher);
        etHeight.removeTextChangedListener(mTextWatcher);
        etInch.removeTextChangedListener(mTextWatcher);
        etDpi.removeTextChangedListener(mTextWatcher);
    }

    private void initView() {
        etWidth = (EditText) findViewById(R.id.et_width);
        etHeight = (EditText) findViewById(R.id.et_height);
        tvTips = (TextView) findViewById(R.id.tv_tips);
        etInch = (EditText) findViewById(R.id.et_inch);
        etDpi = (EditText) findViewById(R.id.et_dpi);
        tvDoCalculate = (TextView) findViewById(R.id.tv_do_calculate);

        etWidth.addTextChangedListener(mTextWatcher);
        etHeight.addTextChangedListener(mTextWatcher);
        etInch.addTextChangedListener(mTextWatcher);
        etDpi.addTextChangedListener(mTextWatcher);
        etInch.setOnFocusChangeListener(mFocusChange);
        etDpi.setOnFocusChangeListener(mFocusChange);
        tvDoCalculate.setOnClickListener(mClick);
        tvDoCalculate.setEnabled(false);
    }

    private void setEditTextStatus() {
        final String width = etWidth.getText().toString();
        final String height = etHeight.getText().toString();
        final String inch = etInch.getText().toString();
        final String dpi = etDpi.getText().toString();
        if (!TextUtils.isEmpty(width) && !TextUtils.isEmpty(height)) {
            etInch.setEnabled(true);
            etDpi.setEnabled(true);
            tvTips.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(inch) || !TextUtils.isEmpty(dpi)) {
                tvDoCalculate.setEnabled(true);
            } else {
                tvDoCalculate.setEnabled(false);
            }
        } else {
            etInch.setEnabled(false);
            etDpi.setEnabled(false);
            tvDoCalculate.setEnabled(false);
            tvTips.setVisibility(View.VISIBLE);
        }
    }

    private void inchToDpi(String inch) {
        final String width = etWidth.getText().toString();
        final String height = etHeight.getText().toString();

        final int widthNumber = Integer.parseInt(width);
        final int heightNumber = Integer.parseInt(height);
        final float inchNumber = Float.parseFloat(inch);

        final double diagonal = Math.sqrt(widthNumber * widthNumber + heightNumber * heightNumber);
        final int dpi = (int) (diagonal / inchNumber);
        etDpi.setText(String.valueOf(dpi));
    }

    private void dpiToInch(String dpi) {
        final String width = etWidth.getText().toString();
        final String height = etHeight.getText().toString();

        final int widthNumber = Integer.parseInt(width);
        final int heightNumber = Integer.parseInt(height);
        final int dpiNumber = Integer.parseInt(dpi);

        final double diagonal = Math.sqrt(widthNumber * widthNumber + heightNumber * heightNumber);
        final double inch = diagonal / dpiNumber;
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        etInch.setText(df.format(inch));
    }
}
