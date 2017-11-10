package com.chinaoly.cp.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Created by yijixin at 2017/11/8
 */
public class RxManager {
    /**
     * 管理Observables 和 Subscribers订阅
     */
    private CompositeSubscription mSubscription = new CompositeSubscription();

    /**
     * 单纯的Observables 和 Subscribers管理
     * @param subscription
     */
    public void add(Subscription subscription){
        /**
         * 订阅管理
         */
        mSubscription.add(subscription);
    }

    /**
     * 单个presenter生命周期结束，取消订阅
     */
    public void clear(){
        /**
         * 取消订阅
         */
        mSubscription.unsubscribe();
    }
}
