package com.cxb.tools.utils;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

/**
 * 虚拟键盘弹出收齐监听
 */
public class KeyboardUtil {

    public static int heightDiff;

//    public static void hideKeyboard(Context context, View view) {
//        WeakReference<View> v = new WeakReference<View>(view);
////        InputMethodManager imm = (InputMethodManager) ((Activity)con.get()).getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive()) {
//            imm.hideSoftInputFromWindow(v.get().getWindowToken(), 0);
//        }
//    }

//    public static void isShow(final View view, final LinearLayout layout) {
//        layout.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//
//                        int heightDiff = layout.getRootView().getHeight() - layout.getHeight();
//                        Log.v("aaaaaaaaaa", "detailMainRL.getRootView().getHeight() = " + layout.getRootView().getHeight());
//                        Log.v("aaaaaaaaaa", "detailMainRL.getHeight() = " + layout.getHeight());
//
//                        if (heightDiff > 100) { // 说明键盘是弹出状态
//                            Log.v("aaaaaaaaaa", "键盘弹出状态");
//                            view.setVisibility(View.GONE);
//                        } else {
//                            Log.v("aaaaaaaaaa", "键盘收起状态");
//                            view.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//    }

    public static void isShow(final View view, final RelativeLayout layout) {

        final WeakReference<View> v = new WeakReference<View>(view);
        final AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,0);
        alphaAnimation.setDuration(10);
        animationSet.addAnimation(alphaAnimation);

        layout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        int height = layout.getRootView().getHeight() - layout.getHeight();
//                        Log.v("aaaaaaaaaa", "detailMainRL.getRootView().getHeight() = " + layout.getRootView().getHeight());
//                        Log.v("aaaaaaaaaa", "detailMainRL.getHeight() = " + layout.getHeight());

                        if (height > 350 && height != heightDiff) { // 说明键盘是弹出状态
//                            Log.v("aaaaaaaaaa", "键盘弹出状态");
                            v.get().setVisibility(View.GONE);
                        } else if(heightDiff != -1 && height != heightDiff){
//                            Log.v("aaaaaaaaaa", "键盘收起状态");
                            v.get().setVisibility(View.VISIBLE);
                            v.get().setAnimation(animationSet);
                        }
                        heightDiff = height;
                    }
                });
    }

    private OnKeyboardShowListener mOnKeyboardShowListener;
    private OnKeyboardHideListener mOnKeyboardHideListener;

    public void keyboardListener(final RelativeLayout layout) {

        layout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        int height = layout.getRootView().getHeight() - layout.getHeight();
//                        Log.v("aaaaaaaaaa", "detailMainRL.getRootView().getHeight() = " + layout.getRootView().getHeight());
//                        Log.v("aaaaaaaaaa", "detailMainRL.getHeight() = " + layout.getHeight());

                        if (height > 350 && height != heightDiff) { // 说明键盘是弹出状态
//                            Log.v("aaaaaaaaaa", "键盘弹出状态");
                            if(mOnKeyboardShowListener != null){
                                mOnKeyboardShowListener.OnKeyboardShowListener();
                            }
                        } else if(heightDiff != -1 && height != heightDiff){
//                            Log.v("aaaaaaaaaa", "键盘收起状态");
                            if(mOnKeyboardHideListener != null){
                                mOnKeyboardHideListener.OnKeyboardHideListener();
                            }
                        }
                        heightDiff = height;
                    }
                });
    }

    public void setOnKeyboardShowListener(OnKeyboardShowListener mOnKeyboardShowListener){
        this.mOnKeyboardShowListener = mOnKeyboardShowListener;
    }

    public interface OnKeyboardShowListener{
        public void OnKeyboardShowListener();
    }

    public void setOnKeyboardHideListener(OnKeyboardHideListener mOnKeyboardHideListener){
        this.mOnKeyboardHideListener = mOnKeyboardHideListener;
    }

    public interface OnKeyboardHideListener{
        public void OnKeyboardHideListener();
    }
}
