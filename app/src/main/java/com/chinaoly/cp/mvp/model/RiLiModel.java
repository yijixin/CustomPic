package com.chinaoly.cp.mvp.model;

import com.chinaoly.cp.base.RxSchedulers;
import com.chinaoly.cp.mvp.contract.RiLiContract;
import com.chinaoly.cp.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * @author Created by yijixin at 2017/11/10
 */
public class RiLiModel implements RiLiContract.Model {
    @Override
    public Observable<List<String>> getCurrentDate(final String year, final String month) {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> list = new ArrayList<>();
                int days = DateUtils.getDaysByYearMonth(Integer.parseInt(year), Integer.parseInt(month));
                String weekDay = DateUtils.getDayOfWeekByDate(year+"-"+month+"-01");
                switch (weekDay){
                    case "周一":
                        list.add("");
                        break;
                    case "周二":
                        for (int i = 0; i < 2; i++) {
                            list.add("");
                        }
                        break;
                    case "周三":
                        for (int i = 0; i < 3; i++) {
                            list.add("");
                        }
                        break;
                    case "周四":
                        for (int i = 0; i < 4; i++) {
                            list.add("");
                        }
                        break;
                    case "周五":
                        for (int i = 0; i < 5; i++) {
                            list.add("");
                        }
                        break;
                    case "周六":
                        for (int i = 0; i < 6; i++) {
                            list.add("");
                        }
                        break;
                    case "周日":
                        break;
                    default:
                        break;
                }
                for (int i = 1; i <= days; i++) {
                    if (i<10){
                        list.add("0"+i);
                    }else{
                        list.add(""+i);
                    }
                }
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<List<String>>io_main());
    }
}
