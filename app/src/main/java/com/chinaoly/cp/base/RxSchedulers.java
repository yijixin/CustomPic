package com.chinaoly.cp.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Created by yijixin at 2017/11/8
 * RxJava的调度管理器
 * Transformer 转换器
 */
public class RxSchedulers {

    public static <T> Observable.Transformer<T,T> io_main(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
