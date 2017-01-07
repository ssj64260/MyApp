package com.cxb.tools.MyProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.cxb.tools.R;

/**
 * 我的进度条
 */

public class MyProgressBar extends ViewGroup {

    private float maxNumber;
    private int backgroundColor;
    private int progressColor;
    private float curProgress;

    private View background;
    private View progressBar;

    public MyProgressBar(Context context) {
        this(context, null, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        maxNumber = Math.abs(ta.getFloat(R.styleable.MyProgressBar_max_number, 100));
        backgroundColor = ta.getColor(R.styleable.MyProgressBar_progress_background, Color.parseColor("#f2f2f2"));
        progressColor = ta.getColor(R.styleable.MyProgressBar_progress_color, Color.parseColor("#87ea31"));
        ta.recycle();

        ViewGroup.LayoutParams bgLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        background = new View(context);
        background.setLayoutParams(bgLayoutParams);
        background.setBackgroundColor(backgroundColor);
        addView(background);

        ViewGroup.LayoutParams pbLayoutParams = new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        progressBar = new View(context);
        progressBar.setLayoutParams(pbLayoutParams);
        progressBar.setBackgroundColor(progressColor);
        addView(progressBar);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);

        measureChild(background, widthMeasureSpec, heightMeasureSpec);
        progressBar.getLayoutParams().width = (int) (curProgress * background.getMeasuredWidth());
        measureChild(progressBar, widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        background.layout(0, 0, background.getMeasuredWidth(), background.getMeasuredHeight());
        progressBar.layout(0, 0, progressBar.getMeasuredWidth(), progressBar.getMeasuredHeight());
    }

    public void setCurrentNumber(float number) {
        if (number < 0) {
            number = 0;
        }
        if (number > maxNumber) {
            number = maxNumber;
        }

        curProgress = number / maxNumber;

        requestLayout();
    }

    public void showAnimation() {
        ScaleAnimation scaleX = new ScaleAnimation(0f, 1f, 1f, 1f);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleX.setFillAfter(true);
        scaleX.setDuration(1500);
        progressBar.startAnimation(scaleX);
    }
}
