package com.cxgk.app.cxgkdemo.logic;

import com.cxgk.app.cxgkdemo.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huangxiaoyu on 2016/9/26.
 */

public class BaseModel<T> implements Serializable {
    public String Success;
    public String ResultText;
    public String ReturnCode;
    public String ReturnText;

    public ArrayList<T> Items;

    public boolean success() {
        return Success.equals(Constants.RESULT_OK);
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "Success='" + Success + '\'' +
                ", ResultText='" + ResultText + '\'' +
                ", ReturnCode='" + ReturnCode + '\'' +
                ", ReturnText='" + ReturnText + '\'' +
                ", Items=" + Items +
                '}';
    }
}
