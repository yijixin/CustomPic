package com.chinaoly.cp.base;

import android.content.Context;

import com.chinaoly.cp.R;
import com.chinaoly.cp.app.AppApplication;
import com.chinaoly.cp.utils.NetWorkUtils;

import rx.Subscriber;

/**
 * @author Created by yijixin at 2017/11/8
 */
public abstract class RxSubscriber<T> extends Subscriber<T>{

    private Context mContext;

    public RxSubscriber(Context context) {
        mContext = context;
    }

    /**
     * dialog时间
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        try{
            if (!NetWorkUtils.isNetConnected(AppApplication.getAppContext())){
                _Error(AppApplication.getAppResource().getString(R.string.no_net));
            }else{
                _Error(e.getMessage());
            }
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }

    @Override
    public void onNext(T t) {
        _Next(t);
    }

    protected abstract void _Next(T t);
    protected abstract void _Error(String message);
}
