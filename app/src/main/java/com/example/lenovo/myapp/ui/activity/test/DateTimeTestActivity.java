package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cxb.tools.utils.DateTimeUtils;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.Date;

/**
 * 日期时间工具类测试
 */

public class DateTimeTestActivity extends BaseActivity {

    private ScrollView svBackground;

    private TextView tvContent;

    private EditText etDate1;
    private EditText etDate2;
    private EditText etTime1;
    private EditText etTime2;

    private Button btnGetDifTime;
    private Button btnGetDifDay;
    private Button btnGetFriendlyDT;
    private Button btnGetDateTime;
    private Button btnClear;

    private RadioGroup rgDate;

    private DateTimeUtils.MaxRange maxRange = DateTimeUtils.MaxRange.YEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime_test);

        initView();

    }

    private void initView() {
        svBackground = (ScrollView) findViewById(R.id.sv_background);

        tvContent = (TextView) findViewById(R.id.tv_content);
        etDate1 = (EditText) findViewById(R.id.et_date1);
        etDate2 = (EditText) findViewById(R.id.et_date2);
        etTime1 = (EditText) findViewById(R.id.et_time1);
        etTime2 = (EditText) findViewById(R.id.et_time2);

        btnGetDifTime = (Button) findViewById(R.id.btn_get_difference_time);
        btnGetDifDay = (Button) findViewById(R.id.btn_get_difference_day);
        btnGetFriendlyDT = (Button) findViewById(R.id.btn_get_friendly_datetime);
        btnGetDateTime = (Button) findViewById(R.id.btn_get_current_datetime);
        btnClear = (Button) findViewById(R.id.btn_clear);

        rgDate = (RadioGroup) findViewById(R.id.rg_date);

        etDate1.setText("2016-11-24");
        etDate2.setText("2016-11-24");
        etTime1.setText("23:33:33");
        etTime2.setText("23:33:33");

        btnGetDifTime.setOnClickListener(click);
        btnGetDifDay.setOnClickListener(click);
        btnGetFriendlyDT.setOnClickListener(click);
        btnGetDateTime.setOnClickListener(click);
        btnClear.setOnClickListener(click);

        rgDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_year:
                        maxRange = DateTimeUtils.MaxRange.YEAR;
                        break;
                    case R.id.rb_month:
                        maxRange = DateTimeUtils.MaxRange.MONTH;
                        break;
                    case R.id.rb_day:
                        maxRange = DateTimeUtils.MaxRange.DAY;
                        break;
                }
            }
        });

    }

    private String getCurrentDateTime(String content) {
        content += "\n【getCurrentDate】\n";
        content += "FULL:  \t" + DateTimeUtils.getCurrentDate(DateTimeUtils.DateType.FULL) + "\n";
        content += "LONG:  \t" + DateTimeUtils.getCurrentDate(DateTimeUtils.DateType.LONG) + "\n";
        content += "MEDIUM:\t" + DateTimeUtils.getCurrentDate(DateTimeUtils.DateType.MEDIUM) + "\n";
        content += "SHORT: \t" + DateTimeUtils.getCurrentDate(DateTimeUtils.DateType.SHORT) + "\n";

        content += "\n【getCurrentTime】\n";
        content += "FULL:  \t" + DateTimeUtils.getCurrentTime(DateTimeUtils.TimeType.FULL) + "\n";
        content += "LONG:  \t" + DateTimeUtils.getCurrentTime(DateTimeUtils.TimeType.LONG) + "\n";
        content += "MEDIUM:\t" + DateTimeUtils.getCurrentTime(DateTimeUtils.TimeType.MEDIUM) + "\n";
        content += "SHORT: \t" + DateTimeUtils.getCurrentTime(DateTimeUtils.TimeType.SHORT) + "\n";

        content += "\n【getCurrentDateTime】\n";
        content += "FULL:  \t" + DateTimeUtils.getCurrentDateTime(DateTimeUtils.DateType.FULL, DateTimeUtils.TimeType.FULL) + "\n";
        content += "LONG:  \t" + DateTimeUtils.getCurrentDateTime(DateTimeUtils.DateType.LONG, DateTimeUtils.TimeType.LONG) + "\n";
        content += "MEDIUM:\t" + DateTimeUtils.getCurrentDateTime(DateTimeUtils.DateType.MEDIUM, DateTimeUtils.TimeType.MEDIUM) + "\n";
        content += "SHORT: \t" + DateTimeUtils.getCurrentDateTime(DateTimeUtils.DateType.SHORT, DateTimeUtils.TimeType.SHORT) + "\n";

        content += "\n【custom】\n";
        content += "getEnDate:       \t" + DateTimeUtils.getEnDate() + "\n";
        content += "getEnNotYearDate:\t" + DateTimeUtils.getEnNotYearDate() + "\n";
        content += "getCnNotYearDate:\t" + DateTimeUtils.getCnNotYearDate() + "\n";
        content += "getCnLongTime:   \t" + DateTimeUtils.getCnLongTime() + "\n";
        content += "getCnShortTime:  \t" + DateTimeUtils.getCnShortTime() + "\n";
        content += "getCnWeek:       \t" + DateTimeUtils.getCnWeek() + "\n";

        return content;

    }

    private boolean checkTime(String time1, String... time2) {
        if (!DateTimeUtils.isTime(time1)) {
            ToastUtil.toast("【时间1】不是时间格式");
            return false;
        } else {
            if (time2 != null && time2.length > 0) {
                if (!DateTimeUtils.isTime(time2[0])) {
                    ToastUtil.toast("【时间2】不是时间格式");
                    return false;
                }
            }

            return true;
        }
    }

    private boolean checkDate(String date1, String... date2) {
        if (!DateTimeUtils.isDate(date1)) {
            ToastUtil.toast("【日期1】不是日期格式");
            return false;
        } else {
            if (date2 != null && date2.length > 0) {
                if (!DateTimeUtils.isDate(date2[0])) {
                    ToastUtil.toast("【日期2】不是日期格式");
                    return false;
                }
            }

            return true;
        }
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String temp = "";

            String content = tvContent.getText().toString();
            String dateText1 = etDate1.getText().toString();
            String dateText2 = etDate2.getText().toString();
            String timeText1 = etTime1.getText().toString();
            String timeText2 = etTime2.getText().toString();

            String datetime1 = dateText1 + " " + timeText1;
            String datetime2 = dateText2 + " " + timeText2;

            Date date1 = DateTimeUtils.StringToDate(datetime1);
            Date date2 = DateTimeUtils.StringToDate(datetime2);

            switch (v.getId()) {
                case R.id.btn_get_difference_time:
                    if (checkDate(dateText1, dateText2) && checkTime(timeText1, timeText2)) {
                        temp = "\n【getNumberOfMinute】\n【相差" + DateTimeUtils.getNumberOfMinute(date1, date2) + "分钟】\n";
                    }
                    break;
                case R.id.btn_get_difference_day:
                    if (checkDate(dateText1, dateText2) && checkTime(timeText1, timeText2)) {
                        temp = "\n【getNumberOfDays】\n【相差" + DateTimeUtils.getNumberOfDays(date1, date2) + "天】\n";
                    }
                    break;
                case R.id.btn_get_friendly_datetime:
                    if (checkDate(dateText1) && checkTime(timeText1)) {
                        temp = "\n【getFriendlyDateTime】\n【" + DateTimeUtils.getFriendlyDateTime(maxRange, datetime1) + "】\n";
                    }
                    break;
                case R.id.btn_get_current_datetime:
                    temp = getCurrentDateTime(content);
                    break;
                case R.id.btn_clear:
                    content = "";
                    break;
            }

            if (!StringCheck.isEmpty(temp)) {
                content += temp;
                Logger.d(temp);
            }

            tvContent.setText(content);
            tvContent.post(new Runnable() {
                @Override
                public void run() {
                    svBackground.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    };
}
