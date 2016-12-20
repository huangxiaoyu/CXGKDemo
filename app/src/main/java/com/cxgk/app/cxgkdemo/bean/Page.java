package com.cxgk.app.cxgkdemo.bean;

/**
 * Created by huangxiaoyu on 2016/9/22.
 */
public class Page {

    private int pageIndex = 1;
    private int pageSize = 15;
    private String oper;
    private boolean refreshAble = true;

    public Page() {
    }

    public Page(String oper) {
        this.oper = oper;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }


    public boolean isRefreshAble() {
        return refreshAble;
    }

    public void setRefreshAble(boolean refreshAble) {
        this.refreshAble = refreshAble;
    }
}
