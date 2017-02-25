package com.example.lenovo.myapp.ui.activity.test;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.cxb.tools.loopview.LoopView;
import com.cxb.tools.loopview.OnItemSelectedListener;
import com.cxb.tools.myprogressbar.MyProgressBar;
import com.cxb.tools.passwordlevel.PasswordLevelLayout;
import com.cxb.tools.textswitcher.MyTextSwitcher;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.db.AddressDBHelper;
import com.example.lenovo.myapp.model.testbean.Area;
import com.example.lenovo.myapp.model.testbean.City;
import com.example.lenovo.myapp.model.testbean.Street;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.PreferencesUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.myapp.utils.PreferencesUtil.APP_SETTING;
import static com.example.lenovo.myapp.utils.PreferencesUtil.KEY_FIRST_START;

/**
 * 自定义控件展示
 */

public class CustomViewTestActivity extends BaseActivity {

    private ScrollView svRoot;

    private EditText etPassword;
    private PasswordLevelLayout pllPwdLevel;

    private EditText etProgress;
    private Button btnConfirm;
    private MyProgressBar mpbProgress;

    private Button btnGetCode;
    private Button btnFirstStart;

    private MyTextSwitcher mtsText;

    private LoopView loopCity;
    private LoopView loopArea;
    private LoopView loopStreet;
    private Button btnGetAddress;

    private List<City> cities;
    private List<Area> areas;
    private List<Street> streets;

    private List<String> cityNames;
    private List<String> areaNames;
    private List<String> streetNames;

    private String curCity;
    private String curArea;
    private String curStreet;

    private int downTime = 60;

    private AddressDBHelper addressDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_test);

        initView();
        setData();

    }

    @Override
    protected void onDestroy() {
        if (mtsText != null) {
            mtsText.stop();
        }
        addressDBHelper.closeDB();
        super.onDestroy();
    }

    private void initView() {
        svRoot = (ScrollView) findViewById(R.id.sv_root);

        etPassword = (EditText) findViewById(R.id.et_password);
        pllPwdLevel = (PasswordLevelLayout) findViewById(R.id.pll_password_level);

        etProgress = (EditText) findViewById(R.id.et_progress);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        mpbProgress = (MyProgressBar) findViewById(R.id.mpb_progress);

        mtsText = (MyTextSwitcher) findViewById(R.id.mts_text);

        loopCity = (LoopView) findViewById(R.id.loop_city);
        loopArea = (LoopView) findViewById(R.id.loop_area);
        loopStreet = (LoopView) findViewById(R.id.loop_street);
        btnGetAddress = (Button) findViewById(R.id.btn_get_address);

        btnGetCode = (Button) findViewById(R.id.btn_get_code);
        btnFirstStart = (Button) findViewById(R.id.btn_first_start);
    }

    private void setData() {
        setLoopMessage();
        setAddress();

        etPassword.addTextChangedListener(watcher);

        btnConfirm.setOnClickListener(click);
        btnGetAddress.setOnClickListener(click);
        btnGetCode.setOnClickListener(click);
        btnFirstStart.setOnClickListener(click);
    }

    private void setLoopMessage() {
        final List<String> texts = new ArrayList<>();
        texts.add("恭喜 <font color='#00a9e7'>乌蝇哥</font> 1分钟前获得 <font color='red'>至尊表情包</font> 的称号");
        texts.add("恭喜 <font color='#00a9e7'>卖萌的猫咪</font> 使用7200张兑换券成功兑换IPhone7 Plus");
        texts.add("恭喜 <font color='#00a9e7'>忍</font> 完成 <font color='blue'>[最强忍耐者]</font> 成就，获得10张富士苹果兑换券");
        texts.add("恭喜 <font color='#00a9e7'>熊本</font> 1分钟前成功兑换了<font color='red'>三星Note7 Power</font>");
        mtsText.getResource(texts);
        mtsText.setOnTextClickListener(new MyTextSwitcher.OnTextClickListener() {
            @Override
            public void onTextClick(int position) {
                ToastUtil.toast(texts.get(position));
            }
        });
    }

    private void setAddress() {
        addressDBHelper = new AddressDBHelper(this);
        cities = new ArrayList<>();
        areas = new ArrayList<>();
        streets = new ArrayList<>();

        cityNames = new ArrayList<>();
        areaNames = new ArrayList<>();
        streetNames = new ArrayList<>();

        if (addressDBHelper.updateDB(this)) {
            cities.clear();
            cities.addAll(addressDBHelper.getCity("6"));
        } else {
            ToastUtil.toast(getString(R.string.toast_datebase_file_not_exists));
        }

        loopCity.setOnTouchListener(touch);
        loopCity.setTextSize(20);
        loopCity.setItemsVisibleCount(7);
        loopCity.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                setAreaNames(index);
            }
        });

        loopArea.setOnTouchListener(touch);
        loopArea.setTextSize(20);
        loopArea.setItemsVisibleCount(7);
        loopArea.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                setStreetNames(index);
            }
        });

        loopStreet.setOnTouchListener(touch);
        loopStreet.setTextSize(20);
        loopStreet.setItemsVisibleCount(7);
        loopStreet.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (index < streetNames.size()) {
                    curStreet = streetNames.get(index);
                } else {
                    curStreet = "";
                }
            }
        });

        setCityNames();
    }

    private void setCityNames() {
        cityNames.clear();
        for (City c : cities) {
            cityNames.add(c.getName());
        }
        if (cityNames.size() <= 0) {
            cityNames.add("");
        }
        loopCity.setInitPosition(0);
        loopCity.setItems(cityNames);
        setAreaNames(0);
    }

    private void setAreaNames(int cityPosition) {
        areaNames.clear();
        areas.clear();
        if (cityPosition < cities.size()) {
            curCity = cities.get(cityPosition).getName();
            List<Area> temp = addressDBHelper.getArea(cities.get(cityPosition).getId());
            if (temp != null) {
                areas.addAll(temp);
                for (Area a : areas) {
                    areaNames.add(a.getName());
                }
            }
        } else {
            curCity = "";
        }

        if (areaNames.size() <= 0) {
            areaNames.add("");
        }
        loopArea.setInitPosition(0);
        loopArea.setItems(areaNames);
        setStreetNames(0);
    }

    private void setStreetNames(int areaPosition) {
        streetNames.clear();
        streets.clear();
        if (areaPosition < areas.size()) {
            curArea = areas.get(areaPosition).getName();
            List<Street> temp = addressDBHelper.getStreet(areas.get(areaPosition).getId());
            if (temp != null) {
                streets.addAll(temp);
                for (Street s : streets) {
                    streetNames.add(s.getName());
                }
            }
        } else {
            curArea = "";
        }

        if (streetNames.size() <= 0) {
            streetNames.add("");
        }
        loopStreet.setInitPosition(0);
        loopStreet.setItems(streetNames);
        curStreet = streetNames.get(0);
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Logger.d(millisUntilFinished);
            downTime--;
            btnGetCode.setText(String.format(getString(R.string.btn_count_down_again), downTime));
        }

        @Override
        public void onFinish() {
            btnGetCode.setText(getString(R.string.btn_count_down_60_second));
            btnGetCode.setClickable(true);
            timer.cancel();
        }
    };

    private View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    svRoot.requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                    svRoot.requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return false;
        }
    };

    //输入框内容改变监听
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            pllPwdLevel.setCurrentLevel(StringCheck.getPasswordLevel(s.toString()));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboard();
            switch (v.getId()) {
                case R.id.btn_confirm:
                    String number = etProgress.getText().toString().trim();
                    if (StringCheck.isOnlyNumber(number)) {
                        hideKeyboard();
                        mpbProgress.setCurrentNumber(Float.parseFloat(number));
                        mpbProgress.showAnimation();
                    } else {
                        ToastUtil.toast(getString(R.string.toast_not_only_number));
                    }
                    break;
                case R.id.btn_get_address:
                    ToastUtil.toast(curCity + " " + curArea + " " + curStreet);
                    break;
                case R.id.btn_get_code:
                    btnGetCode.setClickable(false);
                    downTime = 60;
                    timer.start();
                    break;
                case R.id.btn_first_start:
                    PreferencesUtil.setData(APP_SETTING, KEY_FIRST_START, true);
                    ToastUtil.toast(getString(R.string.toast_open_guide_setting_success));
                    break;
            }
        }
    };

}
