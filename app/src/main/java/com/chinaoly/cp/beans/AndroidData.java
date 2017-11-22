package com.chinaoly.cp.beans;

/**
 * @author Created by yijixin at 2017/11/17
 */
public class AndroidData {
    private String androidName;
    private double percent;
    private int color;

    public AndroidData(String androidName, double percent, int color) {
        this.androidName = androidName;
        this.percent = percent;
        this.color = color;
    }

    public String getAndroidName() {
        return androidName;
    }

    public void setAndroidName(String androidName) {
        this.androidName = androidName;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
