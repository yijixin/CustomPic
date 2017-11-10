package com.chinaoly.cp.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * @author Created by yijixin at 2017/11/8
 */
public class AppApplication extends Application {

    private static AppApplication appApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
    }

    public static Context getAppContext(){
        return appApplication;
    }

    public static Resources getAppResource(){
        return appApplication.getResources();
    }

}
