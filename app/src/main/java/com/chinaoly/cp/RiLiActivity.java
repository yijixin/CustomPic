package com.chinaoly.cp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chinaoly.cp.base.RxBaseActivity;
import com.chinaoly.cp.mvp.contract.RiLiContract;
import com.chinaoly.cp.mvp.model.RiLiModel;
import com.chinaoly.cp.mvp.presenter.RiLiPresenter;
import com.chinaoly.cp.utils.DateUtils;
import com.chinaoly.cp.utils.SpacesItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Yjx
 */
public class RiLiActivity extends RxBaseActivity<RiLiPresenter,RiLiModel> implements RiLiContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_date)
    TextView tvDate;

    private String currentYear,currentDay;
    private int currentMonth;
    private String str;
    private String year,month;
    private int pos = -1;
    private boolean isFlag = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ri_li;
    }

    public static void startAction(Context context){
        context.startActivity(new Intent(context,RiLiActivity.class));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,7));
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(5);
        mRecyclerView.addItemDecoration(itemDecoration);
        currentYear = DateUtils.getCurrentYear();
        currentMonth = DateUtils.getCurrentMonth();
        currentDay = DateUtils.getCurrentDay();
        //最初的值
        year = currentYear;
        month = String.valueOf(currentMonth);

        tvDate.setText(currentYear+"年"+currentMonth+"月");
        mPresenter.backAllDatas(currentYear,String.valueOf(currentMonth));
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void loadDatas(final List<String> mDatas) {
        final CommonAdapter commonAdapter = new CommonAdapter<String>(RiLiActivity.this,R.layout.rili_item,mDatas) {

            @Override
            protected void convert(final ViewHolder holder, String s, final int position) {
                holder.setText(R.id.tv_rili_num,mDatas.get(position));
                //不为空进行背景设置
                if (!TextUtils.isEmpty(mDatas.get(position))){
                    if (year.equals(currentYear)&&month.equals(String.valueOf(currentMonth))&&currentDay.equals(mDatas.get(position))){
                        holder.setTextColorRes(R.id.tv_rili_num,R.color.ziti_03);
                        holder.setBackgroundRes(R.id.tv_rili_num,R.drawable.date_day_bg1);
                    }else{
                        holder.setTextColorRes(R.id.tv_rili_num,R.color.ziti_01);
                        holder.setBackgroundRes(R.id.tv_rili_num,R.drawable.date_day_bg2);

                        holder.setOnClickListener(R.id.tv_rili_num, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                holder.setTextColorRes(R.id.tv_rili_num,R.color.ziti_02);
                                holder.setBackgroundRes(R.id.tv_rili_num,R.drawable.date_day_bg3);

                                //每次点击后上一个消除
                                if (isFlag&&pos!=position){
                                    notifyItemChanged(pos);
                                }
                                isFlag = true;
                                pos = position;
                            }
                        });
                    }
                }
            }
        };
        mRecyclerView.setAdapter(commonAdapter);
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.iv_left,R.id.iv_right})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_left:
                str = tvDate.getText().toString();
                year = str.substring(0,str.indexOf("年"));
                month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                if (Integer.parseInt(month)==1){
                    year = String.valueOf((Integer.parseInt(year)-1));
                    month = "12";
                }else{
                    month = String.valueOf((Integer.parseInt(month)-1));
                }
                tvDate.setText(year+"年"+month+"月");
                mPresenter.backAllDatas(year,month);
                break;
            case R.id.iv_right:
                String str = tvDate.getText().toString();
                year = str.substring(0,str.indexOf("年"));
                month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                if (Integer.parseInt(month)==12){
                    year = String.valueOf((Integer.parseInt(year)+1));
                    month = "1";
                }else{
                    month = String.valueOf((Integer.parseInt(month)+1));
                }
                tvDate.setText(year+"年"+month+"月");
                mPresenter.backAllDatas(year,month);
                break;
            default:
        }
    }
}
