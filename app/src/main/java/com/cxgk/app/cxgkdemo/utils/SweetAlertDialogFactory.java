package com.cxgk.app.cxgkdemo.utils;

import android.content.Context;

import com.cxgk.app.cxgkdemo.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by huangxiaoyu on 2016/11/19.
 */

public class SweetAlertDialogFactory {
    public static SweetAlertDialog build(Context context, int alertType, boolean cancelable) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, alertType).setTitleText("").setConfirmText(context.getString(R.string.text_confirm));
        dialog.setCancelable(cancelable);
        return dialog;
    }

    public static SweetAlertDialog build(Context context, int alertType) {
        return build(context, alertType, true);
    }

    public static SweetAlertDialog build(Context context) {
        return build(context, SweetAlertDialog.NORMAL_TYPE);
    }
}
