package com.chinaoly.cp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinaoly.cp.R;

import java.util.List;

/**
 * @author Created by yijixin at 2017/11/10
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private List<String> mDatas;

    public MyRecyclerAdapter(List<String> datas) {
        mDatas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rili_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvRiLiNum.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvRiLiNum;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvRiLiNum = (TextView) itemView.findViewById(R.id.tv_rili_num);
        }
    }
}
