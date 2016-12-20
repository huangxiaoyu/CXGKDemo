package com.cxgk.app.cxgkdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.bean.Car;
import com.cxgk.app.cxgkdemo.listener.OnItemChildViewClickListener;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/12/13.
 */

public class CarListAdatper extends RecyclerView.Adapter<CarListAdatper.ViewHolder> {
    ArrayList<Car> mDatas;
    OnItemChildViewClickListener mOnItemChildViewClickListener;

    public CarListAdatper(ArrayList<Car> datas, OnItemChildViewClickListener onItemChildViewClickListener) {
        mDatas = datas;
        mOnItemChildViewClickListener = onItemChildViewClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Car car = getItem(position);
        holder.tv_carType.setText(car.getCarType());
        holder.tv_carNumber.setText(car.getCarNum());
        holder.tv_carPrice.setText(car.getPrice() + "");
        holder.tv_buyTime.setText(car.getBuyTime());
        holder.iv_icon.setImageURI(car.getPictureName());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public Car getItem(int position) {
        return mDatas.get(position);
    }

    public void remove(int position) {
        this.mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public void refresh(ArrayList<Car> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void append(ArrayList<Car> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon = (SimpleDraweeView) itemView.findViewById(R.id.iv_icon);
            tv_carType = (TextView) itemView.findViewById(R.id.tv_carType);
            tv_carNumber = (TextView) itemView.findViewById(R.id.tv_carNumber);
            tv_carPrice = (TextView) itemView.findViewById(R.id.tv_carPrice);
            tv_buyTime = (TextView) itemView.findViewById(R.id.tv_buyTime);
        }

        SimpleDraweeView iv_icon;
        TextView tv_carType, tv_carNumber, tv_carPrice, tv_buyTime;
    }
}
