package com.cxgk.app.cxgkdemo.fragment;

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
import com.cxgk.app.cxgkdemo.utils.SweetAlertDialogFactory;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by lenovo on 2016/12/13.
 */

public class StringSortFragment extends Fragment implements View.OnClickListener {
    EditText et_baseStr;
    TextView tv_resultStr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_stringsort, null);
        et_baseStr = (EditText) view.findViewById(R.id.et_baseStr);
        tv_resultStr = (TextView) view.findViewById(R.id.tv_resultStr);
        view.findViewById(R.id.tv_commit).setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onClick(View view) {
        String baseStr = et_baseStr.getText().toString();
        if (TextUtils.isEmpty(baseStr) || !baseStr.contains(" ")) {
            SweetAlertDialogFactory.build(getContext(), SweetAlertDialog.ERROR_TYPE).setContentText("请输入带有空格的字符任意串").show();
            return;
        }
        String[] result = baseStr.split(" ");
        StringBuffer buffer = new StringBuffer();
        for (int i = result.length - 1; i >= 0; i--) {
            buffer.append(result[i] + " ");
        }
        tv_resultStr.setText(buffer.toString());
    }
}
