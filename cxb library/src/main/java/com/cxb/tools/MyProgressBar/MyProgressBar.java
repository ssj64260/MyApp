package com.cxb.tools.myprogressbar;

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

    private float curNumber;
    private float maxNumber;
    private int backgroundColor;
    private int progressResource;
    private boolean showAnimation;

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

        ViewGroup.LayoutParams bgLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        background = new View(context);
        background.setLayoutParams(bgLayoutParams);

        ViewGroup.LayoutParams pbLayoutParams = new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        progressBar = new View(context);
        progressBar.setLayoutParams(pbLayoutParams);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        curNumber = Math.abs(ta.getFloat(R.styleable.MyProgressBar_current_number, 0));
        maxNumber = Math.abs(ta.getFloat(R.styleable.MyProgressBar_max_number, 100));
        showAnimation = ta.getBoolean(R.styleable.MyProgressBar_show_animation, false);

        backgroundColor = ta.getColor(R.styleable.MyProgressBar_progress_background, Color.parseColor("#f2f2f2"));
        background.setBackgroundColor(backgroundColor);

        progressResource = ta.getResourceId(R.styleable.MyProgressBar_progress_color, -1);
        if (progressResource == -1) {
            progressResource = ta.getColor(R.styleable.MyProgressBar_progress_color, Color.parseColor("#87ea31"));
            progressBar.setBackgroundColor(progressResource);
        } else {
            progressBar.setBackgroundResource(progressResource);
        }
        ta.recycle();

        addView(background);
        addView(progressBar);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);

        measureChild(background, widthMeasureSpec, heightMeasureSpec);
        progressBar.getLayoutParams().width = (int) (curNumber / maxNumber * background.getMeasuredWidth());
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

        curNumber = number;

        requestLayout();
    }

    public void showAnimation() {
        if (showAnimation) {
            ScaleAnimation scaleX = new ScaleAnimation(0f, 1f, 1f, 1f);
            scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleX.setFillAfter(true);
            scaleX.setDuration(1500);
            progressBar.startAnimation(scaleX);
        }
    }
}
