package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cxb.tools.utils.DateTimeUtils;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.dialog.DateTimePickerDialog;
import com.example.lenovo.myapp.ui.dialog.DialogListener;
import com.example.lenovo.myapp.utils.ToastMaster;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类测试
 */

public class DateTimeTestActivity extends BaseActivity {

    private ScrollView svBackground;

    private TextView tvContent;

    private TextView tvDate1;
    private TextView tvDate2;
    private TextView tvTime1;
    private TextView tvTime2;

    private Button btnGetDifTime;
    private Button btnGetDifDay;
    private Button btnGetDifMonth;
    private Button btnGetDifYear;
    private Button btnGetFriendlyDT;
    private Button btnGetDateTime;
    private Button btnClear;

    private RadioGroup rgDate;

    private DateTimePickerDialog pickerDialog;

    private int maxRange = DateTimeUtils.YEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime_test);

        initView();

    }

    @Override
    protected void onDestroy() {
        if (pickerDialog != null) {
            pickerDialog.dismiss();
        }
        super.onDestroy();
    }

    private void initView() {
        svBackground = (ScrollView) findViewById(R.id.sv_background);

        tvContent = (TextView) findViewById(R.id.tv_content);
        tvDate1 = (TextView) findViewById(R.id.tv_date1);
        tvDate2 = (TextView) findViewById(R.id.tv_date2);
        tvTime1 = (TextView) findViewById(R.id.tv_time1);
        tvTime2 = (TextView) findViewById(R.id.tv_time2);

        btnGetDifTime = (Button) findViewById(R.id.btn_get_difference_time);
        btnGetDifDay = (Button) findViewById(R.id.btn_get_difference_day);
        btnGetDifMonth = (Button) findViewById(R.id.btn_get_difference_month);
        btnGetDifYear = (Button) findViewById(R.id.btn_get_difference_year);
        btnGetFriendlyDT = (Button) findViewById(R.id.btn_get_friendly_datetime);
        btnGetDateTime = (Button) findViewById(R.id.btn_get_current_datetime);
        btnClear = (Button) findViewById(R.id.btn_clear);

        rgDate = (RadioGroup) findViewById(R.id.rg_date);

        String date = DateTimeUtils.getEnDate();
        String time = DateTimeUtils.getEnLongTime();

        tvDate1.setText(date);
        tvDate2.setText(date);
        tvTime1.setText(time);
        tvTime2.setText(time);

        tvDate1.setOnClickListener(textClick);
        tvDate2.setOnClickListener(textClick);
        tvTime1.setOnClickListener(textClick);
        tvTime2.setOnClickListener(textClick);

        btnGetDifTime.setOnClickListener(btnClick);
        btnGetDifDay.setOnClickListener(btnClick);
        btnGetDifMonth.setOnClickListener(btnClick);
        btnGetDifYear.setOnClickListener(btnClick);
        btnGetFriendlyDT.setOnClickListener(btnClick);
        btnGetDateTime.setOnClickListener(btnClick);
        btnClear.setOnClickListener(btnClick);

        rgDate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_year:
                        maxRange = DateTimeUtils.YEAR;
                        break;
                    case R.id.rb_month:
                        maxRange = DateTimeUtils.MONTH;
                        break;
                    case R.id.rb_day:
                        maxRange = DateTimeUtils.DAY;
                        break;
                }
            }
        });
    }

    private String getCurrentDateTime(String content) {
        content += "\n【getCurrentDate】\n";
        content += "FULL:  \t" + DateTimeUtils.getCurrentDate(DateFormat.FULL) + "\n";
        content += "LONG:  \t" + DateTimeUtils.getCurrentDate(DateFormat.LONG) + "\n";
        content += "MEDIUM:\t" + DateTimeUtils.getCurrentDate(DateFormat.MEDIUM) + "\n";
        content += "SHORT: \t" + DateTimeUtils.getCurrentDate(DateFormat.SHORT) + "\n";

        content += "\n【getCurrentTime】\n";
        content += "FULL:  \t" + DateTimeUtils.getCurrentTime(DateFormat.FULL) + "\n";
        content += "LONG:  \t" + DateTimeUtils.getCurrentTime(DateFormat.LONG) + "\n";
        content += "MEDIUM:\t" + DateTimeUtils.getCurrentTime(DateFormat.MEDIUM) + "\n";
        content += "SHORT: \t" + DateTimeUtils.getCurrentTime(DateFormat.SHORT) + "\n";

        content += "\n【getCurrentDateTime】\n";
        content += "FULL:  \t" + DateTimeUtils.getCurrentDateTime(DateFormat.FULL, DateFormat.FULL) + "\n";
        content += "LONG:  \t" + DateTimeUtils.getCurrentDateTime(DateFormat.LONG, DateFormat.LONG) + "\n";
        content += "MEDIUM:\t" + DateTimeUtils.getCurrentDateTime(DateFormat.MEDIUM, DateFormat.MEDIUM) + "\n";
        content += "SHORT: \t" + DateTimeUtils.getCurrentDateTime(DateFormat.SHORT, DateFormat.SHORT) + "\n";

        content += "\n【custom】\n";
        content += "getEnLongDateTime:\t" + DateTimeUtils.getEnLongDateTime() + "\n";
        content += "getEnDate:        \t" + DateTimeUtils.getEnDate() + "\n";
        content += "getEnNotYearDate: \t" + DateTimeUtils.getEnNotYearDate() + "\n";
        content += "getEnLongTime:    \t" + DateTimeUtils.getEnLongTime() + "\n";
        content += "getEnShortTime:   \t" + DateTimeUtils.getEnShortTime() + "\n";
        content += "getCnLongDateTime:\t" + DateTimeUtils.getCnLongDateTime() + "\n";
        content += "getCnDate:        \t" + DateTimeUtils.getCnDate() + "\n";
        content += "getCnNotYearDate: \t" + DateTimeUtils.getCnNotYearDate() + "\n";
        content += "getCnLongTime:    \t" + DateTimeUtils.getCnLongTime() + "\n";
        content += "getCnShortTime:   \t" + DateTimeUtils.getCnShortTime() + "\n";
        content += "getCnWeek:        \t" + DateTimeUtils.getCnWeek() + "\n";

        return content;

    }

    private boolean checkTime(String time1, String... time2) {
        if (!DateTimeUtils.isTime(time1)) {
            ToastMaster.toast("【时间1】不是时间格式");
            return false;
        } else {
            if (time2 != null && time2.length > 0) {
                if (!DateTimeUtils.isTime(time2[0])) {
                    ToastMaster.toast("【时间2】不是时间格式");
                    return false;
                }
            }

            return true;
        }
    }

    private boolean checkDate(String date1, String... date2) {
        if (!DateTimeUtils.isDate(date1)) {
            ToastMaster.toast("【日期1】不是日期格式");
            return false;
        } else {
            if (date2 != null && date2.length > 0) {
                if (!DateTimeUtils.isDate(date2[0])) {
                    ToastMaster.toast("【日期2】不是日期格式");
                    return false;
                }
            }

            return true;
        }
    }

    private void showPickerDialog(int tag, Calendar calendar, boolean isTimePicker) {
        if (pickerDialog == null) {
            pickerDialog = new DateTimePickerDialog(this);
            pickerDialog.setDialogClickListener(datetimeListener);
        }
        pickerDialog.setDate(tag, calendar, isTimePicker);
        pickerDialog.show();
    }

    // textview点击监听
    private View.OnClickListener textClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_date1:
                    final String dateText1 = tvDate1.getText().toString();
                    final Calendar date1 = Calendar.getInstance();
                    date1.setTime(DateTimeUtils.StringToDateIgnoreTime(dateText1));
                    showPickerDialog(v.getId(), date1, false);
                    break;
                case R.id.tv_date2:
                    final String dateText2 = tvDate2.getText().toString();
                    final Calendar date2 = Calendar.getInstance();
                    date2.setTime(DateTimeUtils.StringToDateIgnoreTime(dateText2));
                    showPickerDialog(v.getId(), date2, false);
                    break;
                case R.id.tv_time1:
                    String timeText1 = tvTime1.getText().toString();
                    Calendar time1 = Calendar.getInstance();
                    time1.setTime(DateTimeUtils.StringToTimeIgnoreDate(timeText1));
                    showPickerDialog(v.getId(), time1, true);
                    break;
                case R.id.tv_time2:
                    String timeText2 = tvTime2.getText().toString();
                    Calendar time2 = Calendar.getInstance();
                    time2.setTime(DateTimeUtils.StringToTimeIgnoreDate(timeText2));
                    showPickerDialog(v.getId(), time2, true);
                    break;
            }
        }
    };

    private DialogListener datetimeListener = new DialogListener() {
        @Override
        public void onConfirmListener(int tag, String content) {
            switch (tag) {
                case R.id.tv_date1:
                    tvDate1.setText(content);
                    break;
                case R.id.tv_date2:
                    tvDate2.setText(content);
                    break;
                case R.id.tv_time1:
                    tvTime1.setText(content);
                    break;
                case R.id.tv_time2:
                    tvTime2.setText(content);
                    break;
            }
        }
    };

    //按钮点击监听
    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String temp = "";

            String content = tvContent.getText().toString();
            String dateText1 = tvDate1.getText().toString();
            String dateText2 = tvDate2.getText().toString();
            String timeText1 = tvTime1.getText().toString();
            String timeText2 = tvTime2.getText().toString();

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
                case R.id.btn_get_difference_month:
                    if (checkDate(dateText1, dateText2) && checkTime(timeText1, timeText2)) {
                        temp = "\n【getNumberOfMonth】\n【相差" + DateTimeUtils.getNumberOfMonth(date1, date2) + "个月】\n";
                    }
                    break;
                case R.id.btn_get_difference_year:
                    if (checkDate(dateText1, dateText2) && checkTime(timeText1, timeText2)) {
                        temp = "\n【getNumberOfYear】\n【相差" + DateTimeUtils.getNumberOfYear(date1, date2) + "年】\n";
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
