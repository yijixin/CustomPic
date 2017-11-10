package com.chinaoly.cp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinaoly.cp.utils.TUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Created by yijixin at 2017/11/8
 */
public abstract class RxBaseFragment<P extends BasePresenter,M extends BaseModel> extends Fragment{

    protected View rootView;
    public P mPresenter;
    public M mModel;
    public RxManager mRxManager;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(getLayoutResource(),container,false);
        }
        mRxManager = new RxManager();
        mUnbinder = ButterKnife.bind(this,rootView);
        mPresenter = TUtils.getT(this,0);
        mModel = TUtils.getT(this,1);
        if (mPresenter != null){
            mPresenter.mContext = this.getActivity();
        }
        initPresenter();
        initView();
        return rootView;
    }

    /**
     * 获取布局文件
     * @return
     */
    protected abstract int getLayoutResource();

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    /**
     * 初始化view
     */
    protected abstract void initView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.onDestroy();
        }
        if (mRxManager!=null){
            mRxManager.clear();
        }
        mUnbinder.unbind();
    }
}
