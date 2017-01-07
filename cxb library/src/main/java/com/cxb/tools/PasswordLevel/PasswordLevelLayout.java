package com.cxb.tools.PasswordLevel;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxb.tools.R;
import com.cxb.tools.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义密码复杂度layout
 */

public class PasswordLevelLayout extends LinearLayout {

    private int maxLevel = 4;
    private LinearLayout llLevelLayout;
    private List<View> views;

    private final int[] levelColor = {
            R.color.password_level_0,
            R.color.password_level_1,
            R.color.password_level_2,
            R.color.password_level_3,
            R.color.password_level_4
    };

    private final String[] levelText = {
            "低","低", "中", "高", "非常高"
    };

    private TextView tvLevelText;

    public PasswordLevelLayout(Context context) {
        this(context, null, 0);
    }

    public PasswordLevelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordLevelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        views = new ArrayList<>();

        setOrientation(LinearLayout.VERTICAL);
        setMinimumHeight(DisplayUtil.dip2px(context, 40));

        llLevelLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        llLevelLayout.setLayoutParams(params);
        llLevelLayout.setOrientation(LinearLayout.HORIZONTAL);
        llLevelLayout.setGravity(Gravity.CENTER_VERTICAL);

        LayoutParams viewParams = new LayoutParams(0, DisplayUtil.dip2px(context, 10), 1f);
        viewParams.setMargins(0, 0, DisplayUtil.dip2px(context, 10), 0);
        for (int i = 0; i < maxLevel; i++) {
            View view = new View(context);
            view.setLayoutParams(viewParams);
            view.setBackgroundResource(R.color.password_level_0);
            views.add(view);
            llLevelLayout.addView(view);
        }

        addView(llLevelLayout);

        tvLevelText = new TextView(context);
        tvLevelText.setLayoutParams(params);
        tvLevelText.setTextColor(Color.parseColor("#D4D4D4"));
        tvLevelText.setText(levelText[0]);
        tvLevelText.setGravity(Gravity.CENTER_VERTICAL);
        addView(tvLevelText);

    }

    public void setCurrentLevel(int curLevel) {

        if (curLevel < 0 || curLevel > 4) {
            throw new IllegalAccessError("level number is [0,4]");
        }

        for (int i = 0; i < maxLevel; i++) {
            views.get(i).setBackgroundResource(levelColor[0]);
        }

        for (int i = 0; i < curLevel; i++) {
            views.get(i).setBackgroundResource(levelColor[curLevel]);
        }

        tvLevelText.setText(levelText[curLevel]);
    }
}
