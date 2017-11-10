package com.chinaoly.cp.mvp.presenter;

import com.chinaoly.cp.base.RxSubscriber;
import com.chinaoly.cp.mvp.contract.RiLiContract;

import java.util.List;

/**
 * @author Created by yijixin at 2017/11/10
 */
public class RiLiPresenter extends RiLiContract.Presenter {
    @Override
    public void backAllDatas(String year, String month) {
        mRxManager.add(mModel.getCurrentDate(year, month).subscribe(new RxSubscriber<List<String>>(mContext) {
            @Override
            protected void _Next(List<String> strings) {
                mView.loadDatas(strings);
            }

            @Override
            protected void _Error(String message) {
                try {
                    mView.showErrorTip(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }));
    }
}
