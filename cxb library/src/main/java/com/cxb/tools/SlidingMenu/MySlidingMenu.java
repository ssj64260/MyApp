package com.cxb.tools.SlidingMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.cxb.tools.utils.DisplayUtil;
import com.cxb.tools.utils.ScreenUtils;

/**
 * 侧滑菜单
 */

public class MySlidingMenu extends ViewGroup {

    private Scroller mScroller;

    private boolean isOpen = false;//是否打开菜单

    private int mScreenWidth;//屏幕宽度
    private int mScreenHeight;//屏幕高度
    private int mMenuRightPadding;

    private ViewGroup mMenu;//菜单view
    private ViewGroup mContent;//主页view

    private int mMenuWidth;
    private int mContentWidth;

    private final int spacePx = 80;//菜单右侧空位宽度
    private final int shortTouchTime = 200;//短触摸时间
    private final int shortTouchWidth = 5;//短触摸长度

    private int mLastX;//手指开始点击X坐标
    private int mLastY;//手指开始点击Y坐标
    private long mLastTime;//手指开始点击时的时间
    private int dx = 0;

    private int mLastXIntercept;
    private int mLastYIntercept;

    public MySlidingMenu(Context context) {
        this(context, null, 0);
    }

    public MySlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new Scroller(context);

        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mScreenHeight = ScreenUtils.getScreenHeight(context);
        //设置Menu距离屏幕右侧的距离，convertToDp是将代码中的100转换成100dp
        mMenuRightPadding = DisplayUtil.convertToDp(context, spacePx);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //拿到Menu，Menu是第0个孩子
        mMenu = (ViewGroup) getChildAt(0);
        //拿到Content，Content是第1个孩子
        mContent = (ViewGroup) getChildAt(1);
        //设置Menu的宽为屏幕的宽度减去Menu距离屏幕右侧的距离
        mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
        //设置Content的宽为屏幕的宽度
        mContentWidth = mContent.getLayoutParams().width = mScreenWidth;
        //测量Menu
        measureChild(mMenu, widthMeasureSpec, heightMeasureSpec);
        //测量Content
        measureChild(mContent, widthMeasureSpec, heightMeasureSpec);
        //测量自己，自己的宽度为Menu宽度加上Content宽度，高度为屏幕高度
        setMeasuredDimension(mMenuWidth + mContentWidth, mScreenHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放Menu的位置，根据上面图可以确定上下左右的坐标
        mMenu.layout(-mMenuWidth, 0, 0, mScreenHeight);
        //摆放Content的位置
        mContent.layout(0, 0, mScreenWidth, mScreenHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        long upTime = System.currentTimeMillis();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mLastTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                if (upTime - mLastTime > shortTouchTime) {
                    mLastTime = System.currentTimeMillis();
                }

                int currentX = (int) event.getX();
                int currentY = (int) event.getY();
                //拿到x方向的偏移量
                dx = currentX - mLastX;
                if (dx < 0) {//向左滑动
                    //边界控制，如果Menu已经完全显示，再滑动的话
                    //Menu左侧就会出现白边了,进行边界控制
                    if (getScrollX() + Math.abs(dx) >= 0) {
                        //直接移动到（0，0）位置，不会出现白边
                        scrollTo(0, 0);
                    } else {//Menu没有完全显示呢
                        //其实这里dx还是-dx，大家不用刻意去记
                        //大家可以先使用dx，然后运行一下，发现
                        //移动的方向是相反的，那么果断这里加个负号就可以了
                        scrollBy(-dx, 0);
                    }
                } else {//向右滑动
                    //边界控制，如果Content已经完全显示，再滑动的话
                    //Content右侧就会出现白边了，进行边界控制
                    if (getScrollX() - dx <= -mMenuWidth) {
                        //直接移动到（-mMenuWidth,0）位置，不会出现白边
                        scrollTo(-mMenuWidth, 0);
                    } else {//Content没有完全显示呢
                        //根据手指移动
                        scrollBy(-dx, 0);
                    }
                }
                mLastX = currentX;
                mLastY = currentY;

                //设置页面交错偏移量
                mMenu.setTranslationX(2 * (mMenuWidth + getScrollX()) / 3);
                break;
            case MotionEvent.ACTION_UP:
                if (upTime - mLastTime > shortTouchTime) {
                    if (getScrollX() < -mScreenWidth / 2) {
                        openMenu();
                    } else {
                        closeMenu();
                    }
                } else {
                    if (dx > shortTouchWidth) {
                        openMenu();
                    } else {
                        closeMenu();
                    }
                }

                Log.d("有个APP", String.valueOf(upTime - mLastTime));
                Log.d("有个APP", getScrollX() + "   " + (-mScreenWidth / 2));
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        // TODO: 2016/11/16 优化触摸事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY) * 2) {//横向滑动
                    intercept = true;
                } else {//纵向滑动
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastTime = System.currentTimeMillis();
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercept;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();

            //设置页面交错偏移量
            mMenu.setTranslationX(2 * (mMenuWidth + getScrollX()) / 3);
        }
    }

    /**
     * 点击开关，开闭Menu，如果当前menu已经打开，则关闭，如果当前menu已经关闭，则打开
     */
    public void toggleMenu() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    /**
     * 关闭menu
     */
    private void closeMenu() {
        //也是使用startScroll方法，dx和dy的计算方法一样
        mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 500);
        invalidate();
        isOpen = false;
    }

    /**
     * 打开menu
     */
    private void openMenu() {
        mScroller.startScroll(getScrollX(), 0, -mMenuWidth - getScrollX(), 0, 500);
        invalidate();
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
