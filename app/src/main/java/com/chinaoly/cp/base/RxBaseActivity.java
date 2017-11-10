package com.chinaoly.cp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chinaoly.cp.app.AppManager;
import com.chinaoly.cp.utils.TUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Created by yijixin at 2017/11/8
 */
public abstract class RxBaseActivity<P extends BasePresenter,M extends BaseModel> extends AppCompatActivity{

    public P mPresenter;
    public M mModel;
    public RxManager mRxManager;
    public Context mContext;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mRxManager = new RxManager();
        mContext = this;
        mUnbinder = ButterKnife.bind(this);
        mPresenter = TUtils.getT(this,0);
        mModel = TUtils.getT(this,1);
        if (mPresenter != null){
            mPresenter.mContext = this;
        }
        initPresenter();
        setTitle();
        initViews(savedInstanceState);
        AppManager.getAppInstance().addActivity(this);
    }

    /**
     * 设置布局Layout
     * @return 返回布局文件
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     * @param savedInstanceState .
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     */
    public abstract void initPresenter();

    /**
     * 头部Title
     */
    public abstract void setTitle();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
        if (mRxManager != null){
            mRxManager.clear();
        }
        mUnbinder.unbind();
    }
}
