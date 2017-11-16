package com.chinaoly.cp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chinaoly.cp.base.RxBaseActivity;
import com.chinaoly.cp.beans.Ability;
import com.chinaoly.cp.view.AbilityLolView;

import butterknife.BindView;

/**
 * @author Yjx 七星能力图
 */
public class AbilityActivity extends RxBaseActivity {

    @BindView(R.id.ability_view)
    AbilityLolView mAbilityLolView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ability;
    }

    public static void startAction(Context context){
        context.startActivity(new Intent(context,AbilityActivity.class));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mAbilityLolView.setAbility(new Ability(99,95,88,100,80,85,99));
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void setTitle() {

    }
}
