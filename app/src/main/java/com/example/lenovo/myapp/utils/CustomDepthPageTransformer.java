package com.example.lenovo.myapp.utils;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lenovo.myapp.R;

/**
 * viewpager 仿UC头条动画
 */

public class CustomDepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.1f;

    @SuppressLint("NewApi")
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        RecyclerView childView = (RecyclerView) view.findViewById(R.id.rv_pm_list);

        if (position < -1) {// [-Infinity,-1)
            childView.setTranslationX(0);
            view.setTranslationX(0);
        } else if (position <= 1) {// [-1,1]
            childView.setTranslationX(pageWidth * getFactor(position));
//            view.setTranslationX(8 * position);

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
