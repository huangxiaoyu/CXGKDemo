package com.cxgk.app.cxgkdemo.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.utils.SweetAlertDialogFactory;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by lenovo on 2016/12/13.
 */

public class AddCarActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_carType, tv_buyTime;
    private EditText et_carNumber, et_carPrice;
    SimpleDraweeView iv_icon;
    String fileName;
    Uri picUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);
        findViewById(R.id.ll_carType).setOnClickListener(this);
        findViewById(R.id.ll_camera).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        tv_carType = (TextView) findViewById(R.id.tv_carType);
        et_carNumber = (EditText) findViewById(R.id.et_carNumber);
        et_carPrice = (EditText) findViewById(R.id.et_carPrice);
        tv_buyTime = (TextView) findViewById(R.id.tv_buyTime);
    }

    @Override
    public void onClick(View view) {
        String carType = tv_carType.getText().toString();
        if (TextUtils.isEmpty(carType)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请选择车型").show();
            return;
        }
        String carNumber = et_carNumber.getText().toString();
        if (TextUtils.isEmpty(carNumber)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请输入车号").show();
            return;
        }
        String carPrice = et_carPrice.getText().toString();
        if (TextUtils.isEmpty(carPrice)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请输入购买价格").show();
            return;
        }
        String buyTime = tv_buyTime.getText().toString();
        if (TextUtils.isEmpty(buyTime)) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请选择购买时间").show();
            return;
        }
        if (picUri == null) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.WARNING_TYPE).setContentText("请选择为车辆拍照").show();
            return;
        }
        //上传
        SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.SUCCESS_TYPE, false).setContentText("车辆已添加").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                finish();
            }
        }).show();
    }

    private Context getContext() {
        return this;
    }
}
