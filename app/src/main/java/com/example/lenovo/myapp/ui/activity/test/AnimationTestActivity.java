package com.example.lenovo.myapp.ui.activity.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.cxb.tools.utils.AnimationUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.base.BaseActivity;


public class AnimationTestActivity extends BaseActivity {

    private Button btnA, btnB, btnC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);

        initView();

    }

    private void initView() {
        btnA = (Button) findViewById(R.id.btn_a);
        btnB = (Button) findViewById(R.id.btn_b);
        btnC = (Button) findViewById(R.id.btn_c);

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toast("A");
                Animation animation = AnimationUtils.loadAnimation(AnimationTestActivity.this, R.anim.test_animation_a);
                btnA.startAnimation(animation);

                AnimatorSet animatorSet = new AnimatorSet();

                ObjectAnimator translationY = ObjectAnimator.ofFloat(btnB, AnimationUtil.translationY, 0f, 100f, 50f, 150f, 80f, 200f, 180f, 400f);
                translationY.setDuration(4000);
                ObjectAnimator translationX = ObjectAnimator.ofFloat(btnB, AnimationUtil.translationX, 0f, 200f);
                translationX.setDuration(4000);

                ObjectAnimator scaleX = ObjectAnimator.ofFloat(btnB, AnimationUtil.scaleX, 1f, 2f, 1f);
                scaleX.setDuration(2000);
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(btnB, AnimationUtil.scaleY, 1f, 2f, 1f);
                scaleY.setDuration(2000);

                ObjectAnimator rotation = ObjectAnimator.ofFloat(btnB, AnimationUtil.rotation, 0f, 360f);
                rotation.setDuration(2000);

                ObjectAnimator rotationX = ObjectAnimator.ofFloat(btnB, AnimationUtil.rotationX, 0f, 180f);
                rotationX.setDuration(2000);
                ObjectAnimator rotationY = ObjectAnimator.ofFloat(btnB, AnimationUtil.rotationY, 0f, 180f);
                rotationY.setDuration(2000);

                ObjectAnimator backgroundColor = ObjectAnimator.ofInt(btnB, AnimationUtil.backgroundColor, 0xFFFFFF00, 0xFFFF0000);
                backgroundColor.setDuration(10000);

                animatorSet.play(translationY).with(translationX);
                animatorSet.play(scaleX).with(scaleY).after(translationY);
                animatorSet.play(rotation).after(scaleX);
                animatorSet.play(rotationX).with(rotationY).after(rotation);
                animatorSet.play(backgroundColor).after(rotationX);
                animatorSet.start();
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toast("B");
                btnC.setVisibility(View.VISIBLE);
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.toast("C");

                ObjectAnimator translationY = ObjectAnimator.ofFloat(btnC, AnimationUtil.translationY, 0f, 50f, 0f);
                translationY.setDuration(500);
                translationY.setRepeatCount(-1);
                translationY.start();

            }
        });
    }
}
