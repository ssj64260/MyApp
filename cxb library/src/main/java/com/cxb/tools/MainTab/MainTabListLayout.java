package com.cxb.tools.MainTab;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxb.tools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 行政服务中心首页头部tab
 */
public class MainTabListLayout extends LinearLayout {

    private static final int viewPagerId = R.id.vp_tab_list;
    private static final int pointLayoutId = R.id.llyout_point_list;

    private OnItemSelectedListener mOnItemSelectedListener;

    private ViewPager mViewPager;

    private LinearLayout pointsLayout;
    private ImageView[] imageViews = {};//标记圆点列表

    private List<MainTab> tabList;

    private List<View> vpList;//viewpager页数

    private int count = 10;//tab每一页多少个功能
    private int spanCount = 5;//一行多少列表

    public MainTabListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        tabList = new ArrayList<>();
        vpList = new ArrayList<>();

        mViewPager = new ViewPager(getContext()) {
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int height = 0;
                //下面遍历所有child的高度
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.measure(widthMeasureSpec,
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    int h = child.getMeasuredHeight();
                    if (h > height) //采用最大的view的高度。
                        height = h;
                }
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                        MeasureSpec.EXACTLY);

                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        };
        mViewPager.setId(viewPagerId);
        LinearLayout.LayoutParams viewPagerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mViewPager.setLayoutParams(viewPagerLayoutParams);
        addView(mViewPager);

        pointsLayout = new LinearLayout(getContext());
        pointsLayout.setId(pointLayoutId);
        int height = getResources().getDimensionPixelSize(R.dimen.custom_weight_16dp);
        LinearLayout.LayoutParams lLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        lLayoutParams.setMargins(0, 0, 0, 0);
        pointsLayout.setLayoutParams(lLayoutParams);
        pointsLayout.setOrientation(HORIZONTAL);
        pointsLayout.setGravity(Gravity.CENTER);
        pointsLayout.setVisibility(GONE);
        addView(pointsLayout);
    }

    //设置每行多少个item
    public void setSpanCount(int spanCount){
        this.spanCount = spanCount;
        count = spanCount * 2;
    }

    //添加item列表
    public void setList(List<MainTab> tabList) {
        this.tabList = tabList;
        initTab();
    }

    private void initTab() {
        int sub = tabList.size() / count;
        int tabPage = tabList.size() % count == 0 ? sub : sub + 1;

        imageViews = new ImageView[tabPage];

        if (tabPage <= 1) {
            pointsLayout.setVisibility(INVISIBLE);
        } else {
            pointsLayout.setVisibility(VISIBLE);
        }

        for (int pageIndex = 0; pageIndex < tabPage; pageIndex++) {
            /* viewpage部分 */
            final View tabView = LayoutInflater.from(getContext()).inflate(R.layout.include_tools_tab_list, null);
            tabView.setTag(pageIndex);
            RecyclerView tab = (RecyclerView) tabView.findViewById(R.id.xrv_main_tab);
            MainTabAdapter tabAdapter = getMainTabAdapter(pageIndex);
            tab.setAdapter(tabAdapter);
            tabAdapter.setOnItemSelectedListener(new MainTabAdapter.OnItemSelectedListener() {
                @Override
                public void onItemClick(View v, int postion) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemClick(v, postion + (count * (int) tabView.getTag()));
                    }
                }
            });
            tab.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
            vpList.add(tabView);


            /* 底部point */
            if (pointsLayout.getVisibility() == VISIBLE) {
                initPoints(pageIndex);
            }
        }

        mViewPager.setAdapter(new ViewPagerAdapter(vpList));
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    //初始化当页对应的point
    private void initPoints(int pageIndex) {
        ImageView ivPoints = new ImageView(getContext());
        int widthHeight = getResources().getDimensionPixelSize(R.dimen.custom_weight_7dp);
        int margin = getResources().getDimensionPixelSize(R.dimen.custom_weight_4dp);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthHeight, widthHeight);
        params.setMargins(margin, 0, margin, 0);
        ivPoints.setLayoutParams(params);
        imageViews[pageIndex] = ivPoints;
        if (pageIndex == 0) {
            ivPoints.setBackgroundResource(R.mipmap.point_select_pop);
        } else {
            ivPoints.setBackgroundResource(R.mipmap.point_unselect);
        }
        pointsLayout.addView(ivPoints);
    }

    //重置pointLayout样式
    private void resetPoints(int position) {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[position]
                    .setBackgroundResource(R.mipmap.point_select_pop);
            if (position != i) {
                imageViews[i]
                        .setBackgroundResource(R.mipmap.point_unselect);
            }
        }
    }

    //获取当页的MainTabAdapter
    private MainTabAdapter getMainTabAdapter(int pageIndex) {
        List<MainTab> onePageTab = new ArrayList<>();
        for (int tabIndex = 0; tabIndex < count; tabIndex++) {
            if (tabIndex + count * pageIndex >= tabList.size()) {
                break;
            } else {
                onePageTab.add(tabList.get(tabIndex + count * pageIndex));
            }
        }
        return new MainTabAdapter(getContext(), onePageTab);
    }

    public interface OnItemSelectedListener {
        void onItemClick(View v, int postion);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener mOnItemSelectedListener) {
        this.mOnItemSelectedListener = mOnItemSelectedListener;
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int i) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            if (pointsLayout.getVisibility() == VISIBLE) {
                resetPoints(position);
            }
        }
    }
}
