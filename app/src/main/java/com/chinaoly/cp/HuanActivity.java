package com.chinaoly.cp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chinaoly.cp.base.RxBaseActivity;

/**
 * @author Yjx
 */
public class HuanActivity extends RxBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_huan;
    }

    public static void startAction(Context context){
        context.startActivity(new Intent(context,HuanActivity.class));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void setTitle() {

    }
}
