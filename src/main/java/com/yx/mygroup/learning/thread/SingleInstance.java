package com.yx.mygroup.learning.thread;

import com.yx.mygroup.model.HouseInfoBean;

public class SingleInstance {

    private static HouseInfoBean houseInfoBean = null;

    private SingleInstance(){}

    public HouseInfoBean getHouseInfoBean () {
        if (houseInfoBean == null) {
            synchronized (SingleInstance.class){
                if (houseInfoBean == null) {
                    houseInfoBean = new HouseInfoBean();
                }
            }
        }
        return houseInfoBean;
    }
}
