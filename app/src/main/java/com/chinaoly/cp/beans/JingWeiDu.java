package com.chinaoly.cp.beans;

/**
 * @author Created by yijixin at 2017/11/16
 */
public class JingWeiDu {

    private double mLongitude;
    private double mLatitude;

    public JingWeiDu(double longitude, double latitude) {
        mLongitude = longitude;
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }
}
