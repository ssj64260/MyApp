package com.cxb.tools.QQLevelLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxb.tools.R;
import com.cxb.tools.utils.DisplayUtil;

/**
 * QQ等级控件
 */

public class QQLevelLayout extends LinearLayout {

    private final int sunLevel = 16;
    private final int moonLevel = 4;
    private final int starLevel = 1;

    private final int iconWidth = 14;
    private final int iconHight = 14;

    private Context context;

    public QQLevelLayout(Context context) {
        super(context);
        this.context = context;
    }

    public QQLevelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public QQLevelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setLevel(int level) {
        if (level < 1) {
            throw new IllegalAccessError("等级只能为正数");
        }

        int curNum = level;

        int sunNum = curNum / sunLevel;
        curNum = curNum % sunLevel;

        int moonNum = curNum / moonLevel;
        curNum = curNum % moonLevel;

        int starNum = curNum;

        int width = DisplayUtil.dip2px(context, iconWidth);
        int hight = DisplayUtil.dip2px(context, iconHight);
        LayoutParams params = new LinearLayout.LayoutParams(width, hight);

        for (int i = 0; i < sunNum; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(R.mipmap.ic_qq_level_sun);
            imageView.setLayoutParams(params);
            addView(imageView);
        }

        for (int i = 0; i < moonNum; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(R.mipmap.ic_qq_level_moon);
            imageView.setLayoutParams(params);
            addView(imageView);
        }

        for (int i = 0; i < starNum; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(R.mipmap.ic_qq_level_star);
            imageView.setLayoutParams(params);
            addView(imageView);
        }

        invalidate();

    }

}
