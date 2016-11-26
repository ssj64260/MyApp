package com.cxb.tools.CustomViewpager;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * viewpager 仿UC头条动画
 */

public class CustomDepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.1f;//缩小份额

    @SuppressLint("NewApi")
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        ViewGroup viewGroup = (ViewGroup) view;

        int childCount = viewGroup.getChildCount();
        View childView;
        if (childCount > 0) {
            childView = viewGroup.getChildAt(0);
        } else {
            return;
        }

        if (position < -1) {// [-Infinity,-1)
            childView.setTranslationX(0);
            view.setTranslationX(0);
        } else if (position <= 1) {// [-1,1]
            childView.setTranslationX(pageWidth * getFactor(position));

            float Margin = 1f - Math.abs(position) * MIN_SCALE;

            view.setScaleX(Margin);
            view.setScaleY(Margin);
        } else {// (1,+Infinity]
            childView.setTranslationX(0);
            view.setTranslationX(0);
        }
    }

    private float getFactor(float position) {
        return -position / 2;
    }
}
