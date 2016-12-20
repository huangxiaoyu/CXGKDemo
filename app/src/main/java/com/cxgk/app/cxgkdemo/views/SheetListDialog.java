package com.cxgk.app.cxgkdemo.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.recycleviewdivider.HorizontalDividerItemDecoration;


/**
 * Created by huangxiaoyu on 2016/11/14.
 */

public class SheetListDialog extends BottomSheetDialog implements View.OnClickListener {
    RecyclerView mRecyclerView;

    public SheetListDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public SheetListDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        init();
    }

    protected SheetListDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_bottomsheetdialog, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.base_line).build());
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
