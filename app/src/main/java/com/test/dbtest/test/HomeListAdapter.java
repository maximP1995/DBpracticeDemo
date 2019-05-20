package com.test.dbtest.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.dbtest.R;

import java.util.ArrayList;

/**
 * Created by zhengmj on 19-4-8.
 */

public class HomeListAdapter extends RecyclerView.Adapter {
    private ArrayList<InfoEntity> list;
    private ClickCallback callback;
    public void setClickCallback(ClickCallback callback){
        this.callback = callback;
    }
    public interface ClickCallback{
        void onClick(int position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_db,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        InfoEntity entity = list.get(position);
        viewHolder.tv_id.setText(String.valueOf(entity.id));
        viewHolder.tv_name.setText(entity.name);
        viewHolder.tv_age.setText(String.valueOf(entity.age));
//        viewHolder.tv_job.setText(entity.job);
        viewHolder.tv_phone.setText(entity.phone);
        viewHolder.ll_whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback!=null)callback.onClick(position);
            }
        });
    }
    public InfoEntity getItemOn(int position){
        return list == null?null:list.get(position);
    }
    public void setList(ArrayList<InfoEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list == null?0:list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_whole;
        private TextView tv_id;
        private TextView tv_name;
        private TextView tv_age;
//        private TextView tv_job;
        private TextView tv_phone;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_whole = itemView.findViewById(R.id.ll_whole);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_age = itemView.findViewById(R.id.tv_age);
//            tv_job = itemView.findViewById(R.id.tv_job);
            tv_phone = itemView.findViewById(R.id.tv_phone);
        }
    }
}
