package com.cxgk.app.cxgkdemo.logic;

import com.cxgk.app.cxgkdemo.utils.Constants;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Huang on 2016/7/26.
 */
public class ApiResponse<T> {

    @SerializedName("id")
    public String code;

    @SerializedName("msg")
    public String msg;

    @SerializedName("result")
    public ArrayList<T> list;
    //总数
    @SerializedName("total")
    public int total;

    public boolean success() {
        return code.equals(Constants.RESULT_OK);
    }

}
