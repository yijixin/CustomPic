package com.chinaoly.cp.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * @author Created by yijixin at 2017/11/8
 */
public class AppManager {

    private static AppManager appManager;
    private static Stack<Activity> activityStack = new Stack<>();

    /**
     * 单例模式
     * @return
     */
    public static AppManager getAppInstance(){
        if (appManager == null){
            appManager = new AppManager();
        }
        return appManager;
    }

    /**
     * 添加
     * @param activity
     */
    public void addActivity(Activity activity){
        activityStack.add(activity);
    }

    /**
     * 获取当前的activity
     * 即最后一个进入堆栈的
     */
    public Activity getCurrentActivity(){
        if (activityStack.size()>0){
            return activityStack.lastElement();
        }
        return null;
    }

    /**
     * 结束指定Activity
     */
    public void finishActivity(Activity activity){
        if (activity != null){
            activityStack.remove(activity);
            activity.finish();
            activity.overridePendingTransition(0,0);
            activity = null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        Activity activity = getCurrentActivity();
        finishActivity(activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void exitApp(Context context){
        try{
            finishAllActivity();
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
