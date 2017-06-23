package com.cxb.tools.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具类
 */

public class ViewAnimationUtils {

    private static int touchX;
    private static int touchY;
    private static int maxRadius;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showFullScreenViewWithMD(@NonNull final View showView, @Nullable final View hideView, Point point) {
        if (!FastClick.isFastClick() && showView.getVisibility() != View.VISIBLE) {
            int screenWidth = DisplayUtil.getScreenWidth();
            int screenHeight = DisplayUtil.getScreenHeight();
            touchX = point.x;
            touchY = point.y;
            int width = touchX > screenWidth / 2 ? touchX : screenWidth - touchX;
            int height = touchY > screenHeight / 2 ? touchY : screenHeight - touchY;

            maxRadius = (int) Math.hypot(width, height);

            Animator animator = android.view.ViewAnimationUtils.createCircularReveal(
                    showView,
                    touchX,
                    touchY,
                    0,
                    maxRadius
            );
            animator.setDuration(400);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (hideView != null) {
                        hideView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
            showView.setVisibility(View.VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void hideFullScreenViewWithMD(@NonNull final View hideView, @Nullable final View showView) {
        if (!FastClick.isFastClick() && hideView.getVisibility() == View.VISIBLE) {
            Animator animator = android.view.ViewAnimationUtils.createCircularReveal(
                    hideView,
                    touchX,
                    touchY,
                    maxRadius,
                    0
            );
            animator.setDuration(400);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    hideView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
            if (showView != null) {
                showView.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void startActivityFromView(Activity activity, Intent intent, View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(
                view,
                view.getWidth() / 2, view.getHeight() / 2,
                0, 0);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void showViewFromRight(@NonNull View showView, @Nullable final View hideView) {
        if (!FastClick.isFastClick() && showView.getVisibility() != View.VISIBLE) {
            TranslateAnimation mShowAction = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f);
            mShowAction.setDuration(300);
            mShowAction.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (hideView != null) {
                        hideView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            showView.setAnimation(mShowAction);
            showView.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViewToRight(@NonNull View hideView, @Nullable View showView) {
        if (!FastClick.isFastClick() && hideView.getVisibility() == View.VISIBLE) {
            TranslateAnimation mHiddenAction = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0f,
                    Animation.RELATIVE_TO_SELF, 0f);
            mHiddenAction.setDuration(300);
            hideView.setAnimation(mHiddenAction);
            hideView.setVisibility(View.GONE);
            if (showView != null) {
                showView.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void showViewWithAlpha(@NonNull View showView, @Nullable final View hideView) {
        if (!FastClick.isFastClick() && showView.getVisibility() != View.VISIBLE) {
            AlphaAnimation mShowAction = new AlphaAnimation(0.1f, 1f);
            mShowAction.setDuration(300);
            mShowAction.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (hideView != null) {
                        hideView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            showView.setAnimation(mShowAction);
            showView.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViewWithAlpha(@NonNull View hideView, @Nullable View showView) {
        if (!FastClick.isFastClick() && hideView.getVisibility() == View.VISIBLE) {
            AlphaAnimation mHiddenAction = new AlphaAnimation(1, 0.1f);
            mHiddenAction.setDuration(300);
            hideView.setAnimation(mHiddenAction);
            hideView.setVisibility(View.GONE);
            if (showView != null) {
                showView.setVisibility(View.VISIBLE);
            }
        }
    }
}
