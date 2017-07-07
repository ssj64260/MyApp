package com.example.lenovo.myapp.ui.activity.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cxb.tools.utils.DisplayUtil;
import com.cxb.tools.utils.ViewAnimationUtils;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;

import static android.view.View.ROTATION;
import static android.view.View.ROTATION_X;
import static android.view.View.ROTATION_Y;
import static android.view.View.SCALE_X;
import static android.view.View.SCALE_Y;
import static android.view.View.TRANSLATION_X;
import static android.view.View.TRANSLATION_Y;
import static com.cxb.tools.utils.AnimationUtil.BACKGROUND_COLOR;


public class AnimationTestActivity extends BaseActivity {

    private ImageView ivCatLoading, ivLoading, ivRedLoading, ivBigLoading, ivHeartLoading;
    private Button btnMaterialDesign, btnA, btnB, btnC;
    private LinearLayout llButtons;
    private View background;

    private AnimationDrawable catLoadingDrawable;
    private AnimationDrawable loadingDrawable;
    private AnimationDrawable redLoadingDrawable;
    private AnimationDrawable bigLoadingDrawable;
    private AnimationDrawable heartLoadingDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);

        initView();
        setData();
    }

    private void initView() {
        ivCatLoading = (ImageView) findViewById(R.id.iv_cat_loading);
        ivLoading = (ImageView) findViewById(R.id.iv_loading);
        ivRedLoading = (ImageView) findViewById(R.id.iv_red_loading);
        ivBigLoading = (ImageView) findViewById(R.id.iv_big_loading);
        ivHeartLoading = (ImageView) findViewById(R.id.iv_heart_loading);

        btnMaterialDesign = (Button) findViewById(R.id.btn_material_design);
        btnA = (Button) findViewById(R.id.btn_a);
        btnB = (Button) findViewById(R.id.btn_b);
        btnC = (Button) findViewById(R.id.btn_c);

        llButtons = (LinearLayout) findViewById(R.id.ll_buttons);
        background = findViewById(R.id.view_background);
    }

    private void setData() {
        ivCatLoading.setOnClickListener(click);
        ivLoading.setOnClickListener(click);
        ivRedLoading.setOnClickListener(click);
        ivBigLoading.setOnClickListener(click);
        ivHeartLoading.setOnClickListener(click);

        btnMaterialDesign.setOnClickListener(click);
        btnA.setOnClickListener(click);
        btnB.setOnClickListener(click);
        btnC.setOnClickListener(click);

        background.setOnClickListener(click);

        catLoadingDrawable = (AnimationDrawable) ivCatLoading.getDrawable();
        loadingDrawable = (AnimationDrawable) ivLoading.getDrawable();
        redLoadingDrawable = (AnimationDrawable) ivRedLoading.getDrawable();
        bigLoadingDrawable = (AnimationDrawable) ivBigLoading.getDrawable();
        heartLoadingDrawable = (AnimationDrawable) ivHeartLoading.getDrawable();
    }

    private void startAnimation() {
        ToastMaster.toast("A");
        Animation animation = AnimationUtils.loadAnimation(AnimationTestActivity.this, R.anim.test_animation_a);
        btnA.startAnimation(animation);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator translationY = ObjectAnimator.ofFloat(btnB, TRANSLATION_Y, 0f, 100f, 50f, 150f, 80f, 200f, 180f, 400f);
        translationY.setDuration(4000);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(btnB, TRANSLATION_X, 0f, 200f);
        translationX.setDuration(4000);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(btnB, SCALE_X, 1f, 2f, 1f);
        scaleX.setDuration(2000);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(btnB, SCALE_Y, 1f, 2f, 1f);
        scaleY.setDuration(2000);

        ObjectAnimator rotation = ObjectAnimator.ofFloat(btnB, ROTATION, 0f, 360f);
        rotation.setDuration(2000);

        ObjectAnimator rotationX = ObjectAnimator.ofFloat(btnB, ROTATION_X, 0f, 180f);
        rotationX.setDuration(2000);
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(btnB, ROTATION_Y, 0f, 180f);
        rotationY.setDuration(2000);

        ObjectAnimator backgroundColor = ObjectAnimator.ofInt(btnB, BACKGROUND_COLOR, Color.parseColor("#FFFFFF00"), Color.parseColor("#FFFF0000"));
        backgroundColor.setDuration(10000);

        animatorSet.play(translationY).with(translationX);
        animatorSet.play(scaleX).with(scaleY).after(translationY);
        animatorSet.play(rotation).after(scaleX);
        animatorSet.play(rotationX).with(rotationY).after(rotation);
        animatorSet.play(backgroundColor).after(rotationX);
        animatorSet.start();
    }

    private void showViewBackground() {
        int statusBarHeight = DisplayUtil.getStatusBarHeight();
        int[] position = new int[2];
        btnMaterialDesign.getLocationOnScreen(position);

        int viewWidth = btnMaterialDesign.getWidth();
        int viewHeight = btnMaterialDesign.getHeight();

        int pointX = position[0] + viewWidth / 2;
        int pointY = position[1] - statusBarHeight + viewHeight / 2;

        ViewAnimationUtils.showFullScreenViewWithMD(background, llButtons, new Point(pointX, pointY));
    }

    private void hideViewBackgound() {
        ViewAnimationUtils.hideFullScreenViewWithMD(background, llButtons);
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_cat_loading:
                    if (catLoadingDrawable.isRunning()) {
                        catLoadingDrawable.stop();
                    } else {
                        catLoadingDrawable.start();
                    }
                    break;
                case R.id.iv_loading:
                    if (loadingDrawable.isRunning()) {
                        loadingDrawable.stop();
                    } else {
                        loadingDrawable.start();
                    }
                    break;
                case R.id.iv_red_loading:
                    if (redLoadingDrawable.isRunning()) {
                        redLoadingDrawable.stop();
                    } else {
                        redLoadingDrawable.start();
                    }
                    break;
                case R.id.iv_big_loading:
                    if (bigLoadingDrawable.isRunning()) {
                        bigLoadingDrawable.stop();
                    } else {
                        bigLoadingDrawable.start();
                    }
                    break;
                case R.id.iv_heart_loading:
                    if (heartLoadingDrawable.isRunning()) {
                        heartLoadingDrawable.stop();
                    } else {
                        heartLoadingDrawable.start();
                    }
                    break;
                case R.id.btn_material_design:
                    showViewBackground();
                    break;
                case R.id.btn_a:
                    startAnimation();
                    break;
                case R.id.btn_b:
                    ToastMaster.toast("B");
                    btnC.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_c:
                    ToastMaster.toast("C");

                    ObjectAnimator translationY = ObjectAnimator.ofFloat(btnC, TRANSLATION_Y, 0f, 50f, 0f);
                    translationY.setDuration(500);
                    translationY.setRepeatCount(-1);
                    translationY.start();
                    break;
                case R.id.view_background:
                    hideViewBackgound();
                    break;
            }
        }
    };
}
