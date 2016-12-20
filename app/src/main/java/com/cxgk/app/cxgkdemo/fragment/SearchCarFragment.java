package com.cxgk.app.cxgkdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.activity.SearchResultActivity;
import com.cxgk.app.cxgkdemo.adapter.SheetListAdapter;
import com.cxgk.app.cxgkdemo.listener.OnItemChildViewClickListener;
import com.cxgk.app.cxgkdemo.utils.Constants;
import com.cxgk.app.cxgkdemo.utils.SweetAlertDialogFactory;
import com.cxgk.app.cxgkdemo.views.SheetListDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 车辆查询
 * Created by lenovo on 2016/12/14.
 */

public class SearchCarFragment extends Fragment implements View.OnClickListener {

    private TextView tv_carType;
    private EditText et_carNumber;
    SheetListDialog mCarListTypeDialog;
    SheetListAdapter mCarTypeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_searchcar, null);
        view.findViewById(R.id.ll_carType).setOnClickListener(this);
        view.findViewById(R.id.tv_commit).setOnClickListener(this);
        tv_carType = (TextView) view.findViewById(R.id.tv_carType);
        et_carNumber = (EditText) view.findViewById(R.id.et_carNumber);
        String carTypeStr = getString(R.string.car_type);
        try {
            JSONArray carTypearrArray = new JSONArray(carTypeStr);
            mCarTypeAdapter = new SheetListAdapter(carTypearrArray, "name");
            mCarTypeAdapter.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
                @Override
                public void onItemChildViewClickListener(int id, int position) {
                    mCarListTypeDialog.dismiss();
                    JSONObject object = mCarTypeAdapter.getItem(position);
                    try {
                        tv_carType.setText(object.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            mCarListTypeDialog = new SheetListDialog(getActivity());
            mCarListTypeDialog.setAdapter(mCarTypeAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                String carType = tv_carType.getText().toString();
                String carNum = et_carNumber.getText().toString();
                if (TextUtils.isEmpty(carType)) {
                    SweetAlertDialogFactory.build(getActivity(), SweetAlertDialog.WARNING_TYPE).setContentText("请选择车型").show();
                    return;
                }
                if (TextUtils.isEmpty(carNum)) {
                    SweetAlertDialogFactory.build(getActivity(), SweetAlertDialog.WARNING_TYPE).setContentText("请输入车号").show();
                    return;
                }
                intent.putExtra(Constants.KEY_DEFAULE, carType);
                intent.putExtra(Constants.KEY_OBJ1, carNum);
                startActivity(intent);
                break;
            case R.id.ll_carType:
                if (mCarListTypeDialog.isShowing()) {
                    mCarListTypeDialog.dismiss();
                } else {
                    mCarListTypeDialog.show();
                }
                break;
        }
    }
}
