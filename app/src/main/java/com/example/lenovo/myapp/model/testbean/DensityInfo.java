package com.example.lenovo.myapp.model.testbean;

import android.util.DisplayMetrics;

/**
 * 宽度信息
 */

public class DensityInfo {

    private String densityName;
    private float density;
    private float dp = 0f;
    private float px = 0f;

    public DensityInfo(String name, float density) {
        this.densityName = name;
        this.density = density;
    }

    public void doCalculateDP(float inputDensity, float inputDP) {
        dp = density / inputDensity * inputDP;
        px = dp2px(dp);
    }

    public void doCalculatePX(float inputDensity, float inputPX) {
        px = density / inputDensity * inputPX;
        dp = px2dp(px);
    }

    private float dp2px(float value) {
        return value * density / (float) DisplayMetrics.DENSITY_MEDIUM;
    }

    private float px2dp(float value) {
        return value / (density / (float) DisplayMetrics.DENSITY_MEDIUM);
    }

    public String getDensityName() {
        return densityName;
    }

    public void setDensityName(String densityName) {
        this.densityName = densityName;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getDp() {
        return dp;
    }

    public void setDp(float dp) {
        this.dp = dp;
    }

    public float getPx() {
        return px;
    }

    public void setPx(float px) {
        this.px = px;
    }
}
