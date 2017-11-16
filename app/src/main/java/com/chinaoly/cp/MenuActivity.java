package com.chinaoly.cp;

import android.os.Bundle;
import android.view.View;

import com.chinaoly.cp.base.RxBaseActivity;

import butterknife.OnClick;

/**
 * @author Yjx 主界面
 */
public class MenuActivity extends RxBaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_menu;
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

    @OnClick({R.id.btn_yuanhuan,R.id.btn_bolang,R.id.btn_img,R.id.btn_lol,R.id.btn_rili,R.id.btn_ruler,R.id.btn_gaode,R.id.btn_pic})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_yuanhuan:
                HuanActivity.startAction(MenuActivity.this);
                break;
            case R.id.btn_bolang:
                MainActivity.startAction(MenuActivity.this);
                break;
            case R.id.btn_img:
                ImageActivity.startAction(MenuActivity.this);
                break;
            case R.id.btn_lol:
                AbilityActivity.startAction(MenuActivity.this);
                break;
            case R.id.btn_rili:
                RiLiActivity.startAction(MenuActivity.this);
                break;
            case R.id.btn_ruler:
                RulerActivity.startAction(MenuActivity.this);
                break;
            case R.id.btn_gaode:
                GaoDeMapActivity.startAction(MenuActivity.this);
                break;
            case R.id.btn_pic:
                PicActivity.startAction(MenuActivity.this);
                break;
            default:
                break;
        }
    }
}
