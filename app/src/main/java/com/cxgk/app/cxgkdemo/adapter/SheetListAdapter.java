package com.cxgk.app.cxgkdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.listener.OnItemChildViewClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by huangxiaoyu on 2016/11/14.
 */

public class SheetListAdapter extends RecyclerView.Adapter<SheetListAdapter.Viewholder> {
    JSONArray mArray;
    String key;
    OnItemChildViewClickListener mOnItemChildViewClickListener;

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        mOnItemChildViewClickListener = onItemChildViewClickListener;
    }

    public SheetListAdapter(JSONArray array, String key) {
        mArray = array;
        this.key = key;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_center_text, parent, false);
        return new Viewholder(item);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemChildViewClickListener != null) {
                    mOnItemChildViewClickListener.onItemChildViewClickListener(-1, position);
                }
            }
        });
        try {
            JSONObject object = mArray.getJSONObject(position);
            holder.tv_text.setText(object.getString(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mArray.length();
    }

    public JSONObject getItem(int position) {
        try {
            return mArray.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    class Viewholder extends RecyclerView.ViewHolder {

        public Viewholder(View itemView) {
            super(itemView);

            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
        }

        TextView tv_text;
    }
}
