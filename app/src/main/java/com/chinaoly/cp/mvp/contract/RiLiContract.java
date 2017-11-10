package com.chinaoly.cp.mvp.contract;

import com.chinaoly.cp.base.BaseModel;
import com.chinaoly.cp.base.BasePresenter;
import com.chinaoly.cp.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * @author Created by Administrator on 2017/11/10.
 */
public interface RiLiContract {
    interface Model extends BaseModel{
        /**
         * 返回当前选中月的日期
         * @param year
         * @param month
         * @return
         */
        Observable<List<String>> getCurrentDate(String year,String month);
    }

    interface View extends BaseView{
        /**
         * 添加数据
         * @param mDatas
         */
        void loadDatas(List<String> mDatas);
    }

    abstract static class Presenter extends BasePresenter<View,Model>{
        /**
         * 通过年月获取数据
         * @param year
         * @param month
         */
        public abstract void backAllDatas(String year,String month);
    }
}
