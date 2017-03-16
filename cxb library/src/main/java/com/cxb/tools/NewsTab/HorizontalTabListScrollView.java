package com.cxb.tools.newstab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxb.tools.R;
import com.cxb.tools.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯头部tab layout
 */
public class HorizontalTabListScrollView extends HorizontalScrollView {

    private LinearLayout mHorizontalTabView;
    private View topTempView;//当前选中的view对象

    private List<String> tabList;
    private OnItemSelectedListener mOnItemSelectedListener;//点击回调

    private int textColorNormal = Color.parseColor("#FF707070");//未选中的字体颜色
    private int textColorSelect = Color.parseColor("#FF0295C9");//选中的字体颜色
    private float textSize = 0f;//字体大小px
    private int reserveTab = 1;//左边预留tab个数

    public HorizontalTabListScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        initAttrs(context, attrs);
        init();
    }

    public void init() {
        tabList = new ArrayList<>();
        mHorizontalTabView = new LinearLayout(getContext());
        mHorizontalTabView.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        mHorizontalTabView.setLayoutParams(layoutParams);
        this.addView(mHorizontalTabView);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HorizontalTabListScrollView);
        textColorNormal = ta.getColor(R.styleable.HorizontalTabListScrollView_ht_unselect_text_color, Color.parseColor("#FF707070"));
        textColorSelect = ta.getColor(R.styleable.HorizontalTabListScrollView_ht_select_text_color, Color.parseColor("#FF0295C9"));
        textSize = ta.getDimension(R.styleable.HorizontalTabListScrollView_ht_text_size, DisplayUtil.sp2px(getContext(), 14f));
        reserveTab = ta.getInt(R.styleable.HorizontalTabListScrollView_ht_left_reserve_tab, 1);
        ta.recycle();
    }

    public void addTabList(List<String> tabList) {
        this.tabList = tabList;
        initTab();
    }

    //初始化tab
    private void initTab() {
        mHorizontalTabView.removeAllViews();
        LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final int count = tabList.size();
        for (int i = 0; i < count; i++) {
            final View itemView = mLayoutInflater.inflate(R.layout.item_news_tab, null);
            final TextView name = (TextView) itemView.findViewById(R.id.tv_name);

            String cateName = tabList.get(i);

            if (textSize > 0) {
                name.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            name.setText(cateName);
            name.setTextColor(textColorNormal);
            itemView.setTag(i);

            if (i == 0) {
                topTempView = itemView;
                name.setTextColor(textColorSelect);
                itemView.setClickable(false);
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_big);
                animation.setFillAfter(true);
                name.startAnimation(animation);
            }

            //头部菜单按钮点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    int position = (int) v.getTag();

                    setTabView(position, v);

                    //item点击回调
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemClick(v, position);
                    }
                }
            });

            itemView.setLayoutParams(layoutParams);
            mHorizontalTabView.addView(itemView);
        }
        scrollTo(0, 0);
    }

    //滑动改变页面时，同时改变头部tab样式
    public void changeTabByPostion(int position) {
        setTabView(position, mHorizontalTabView.getChildAt(position));
    }

    //获得当前位置的X坐标
    private int getXCoordinate(int position) {
        int allWidth = 0;
        for (int i = 0; i < (position - reserveTab); i++) {
            allWidth += mHorizontalTabView.getChildAt(i).getWidth();
        }
        return allWidth;
    }

    //设置按钮样式
    private void setTabView(int position, View selectView) {

        //拿到点击前按钮的对象，并把样式还原
        topTempView.setClickable(true);
        TextView tempName = (TextView) topTempView.findViewById(R.id.tv_name);
        tempName.setTextColor(textColorNormal);
        Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.scale_small);
        animation1.setFillAfter(true);
        tempName.startAnimation(animation1);

        //记录当前点击按钮的对象
        topTempView = selectView;

        //设置当前点击按钮的样式
        topTempView.setClickable(false);
        tempName = (TextView) topTempView.findViewById(R.id.tv_name);
        tempName.setTextColor(textColorSelect);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_big);
        animation.setFillAfter(true);
        tempName.startAnimation(animation);

        //设置跳转的位置
        smoothScrollTo(getXCoordinate(position), 0);
    }

    public interface OnItemSelectedListener {
        void onItemClick(View v, int postion);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener mOnItemSelectedListener) {
        this.mOnItemSelectedListener = mOnItemSelectedListener;
    }

}
