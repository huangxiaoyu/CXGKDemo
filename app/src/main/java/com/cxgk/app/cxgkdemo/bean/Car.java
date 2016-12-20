package com.cxgk.app.cxgkdemo.bean;

/**
 * Created by lenovo on 2016/12/13.
 */

public class Car extends BaseBean {


    /**
     * ID : 505
     * strID : 9e517909-1927-4ecf-9432-470f63b0f0db
     * CarType : 轿车
     * CarNum : 2
     * BuyTime : 2016-12-14 00:00:00
     * Price : 5656
     * PictureName : IMG_20161214_151622.jpg
     */

    private String strID;
    private String CarType;
    private String CarNum;
    private String BuyTime;
    private String Price;
    private String PictureName;

    public String getStrID() {
        return strID;
    }

    public void setStrID(String strID) {
        this.strID = strID;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String CarType) {
        this.CarType = CarType;
    }

    public String getCarNum() {
        return CarNum;
    }

    public void setCarNum(String CarNum) {
        this.CarNum = CarNum;
    }

    public String getBuyTime() {
        return BuyTime;
    }

    public void setBuyTime(String BuyTime) {
        this.BuyTime = BuyTime;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getPictureName() {
        return PictureName;
    }

    public void setPictureName(String PictureName) {
        this.PictureName = PictureName;
    }
}
