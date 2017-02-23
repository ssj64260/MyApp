package com.example.lenovo.myapp.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍页面
 */

public class GuideActivity extends BaseActivity {

    private ViewPager mViewPager;
    private ViewGroup vgPoints;

    private List<ImageView> pointList;

    private static final int[] pics = {
            R.drawable.img_instinct,
            R.drawable.img_valor,
            R.drawable.img_mystic
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
        setData();

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_views);
        vgPoints = (ViewGroup) findViewById(R.id.viewGroup);
    }

    private void setData() {
        findViewById(R.id.tv_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(GuideActivity.this, PokemonMainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        List<View> viewList = new ArrayList<>();
        pointList = new ArrayList<>();

        int Width = getApplicationContext().getResources().getDisplayMetrics().widthPixels/40;

        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.viewpager_introduction, null);
            ImageView image = (ImageView) view.findViewById(R.id.iv_introduction);
            image.setImageResource(pics[i]);
            viewList.add(view);

            ImageView ivPoint = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Width, Width);
            params.setMargins(Width/2, 0, Width/2, 0);
            ivPoint.setLayoutParams(params);
            pointList.add(ivPoint);
            if (i == 0) {
                pointList.get(i).setBackgroundResource(R.drawable.point_select_pop);
            } else {
                pointList.get(i).setBackgroundResource(R.drawable.point_unselect_white);
            }
            vgPoints.addView(ivPoint);
        }

        mViewPager.setAdapter(new ViewPagerAdapter(viewList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pointList.size(); i++) {
                    pointList.get(i).setBackgroundResource(R.drawable.point_unselect_white);
                    if (position == i) {
                        pointList.get(i).setBackgroundResource(R.drawable.point_select_pop);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
