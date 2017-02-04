package com.cxb.tools.textswitcher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.cxb.tools.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 文字上下滚动控件
 */
public class MyTextSwitcher extends TextSwitcher {

    private List<String> reArrayList = new ArrayList<>();
    private int resIndex = -1;
    private final int UPDATE_TEXTSWITCHER = 1;

    private ScheduledExecutorService scheduledThreadPool = null;

    private OnTextClickListener onTextClickListener;

    private long period = 5;//周期时间

    public MyTextSwitcher(Context context) {
        super(context);
        init();
    }

    public MyTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTextSwitcher);
        period = Math.abs(ta.getInt(R.styleable.MyTextSwitcher_period, 5));
        ta.recycle();
        init();
    }

    private void init() {
        setFactory(factory);
        setInAnimation(getContext(), R.anim.in_from_bottom);
        setOutAnimation(getContext(), R.anim.out_to_top);

        if (scheduledThreadPool == null || scheduledThreadPool.isShutdown()) {
            scheduledThreadPool = Executors.newScheduledThreadPool(1);
        }
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = UPDATE_TEXTSWITCHER;
                handler.sendMessage(msg);
            }
        }, 0, period, TimeUnit.SECONDS);
    }

    public void getResource(List<String> reArrayList) {
        this.reArrayList = reArrayList;
    }

    public void updateTextSwitcher() {
        if (reArrayList != null && reArrayList.size() > 0) {
            resIndex++;
            if (resIndex >= reArrayList.size()) {
                resIndex = 0;
            }
            setText(Html.fromHtml(reArrayList.get(resIndex)));
        }
    }

    public void stop() {
        if (scheduledThreadPool != null) {
            try {
                scheduledThreadPool.shutdown();
                if (!scheduledThreadPool.awaitTermination(0, TimeUnit.MILLISECONDS)) {
                    scheduledThreadPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                scheduledThreadPool.shutdownNow();
            }
        }

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXTSWITCHER:
                    updateTextSwitcher();
                    break;
                default:
                    break;
            }
        }
    };

    private ViewFactory factory = new ViewFactory() {
        @Override
        public View makeView() {
            TextView textView = new TextView(getContext());
            textView.setSingleLine();
            textView.setTextSize(12);
            textView.setTextColor(Color.parseColor("#808080"));
            textView.setEllipsize(TextUtils.TruncateAt.END);
            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.gravity = Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(lp);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTextClickListener != null) {
                        if (resIndex < 0) {
                            resIndex = 0;
                        }
                        onTextClickListener.onTextClick(resIndex);
                    }
                }
            });
            return textView;
        }
    };

    public interface OnTextClickListener {
        public void onTextClick(int position);
    }

    public void setOnTextClickListener(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }
}
