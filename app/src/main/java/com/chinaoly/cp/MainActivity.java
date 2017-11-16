package com.chinaoly.cp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chinaoly.cp.base.RxBaseActivity;

/**
 * @author Yjx
 */
public class MainActivity extends RxBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public static void startAction(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
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
