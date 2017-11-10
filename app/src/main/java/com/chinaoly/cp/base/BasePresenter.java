package com.chinaoly.cp.base;

import android.content.Context;

/**
 * @author Created by yijixin at 2017/11/8
 */
public abstract class BasePresenter<V,M> {

    public Context mContext;
    public V mView;
    public M mModel;
    public RxManager mRxManager = new RxManager();

    public void setVM(V v,M m){
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart(){

    }

    public void onDestroy() {
        mRxManager.clear();
    }
}
