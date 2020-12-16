package com.yx.springboot.demospring.thread;

import com.yx.springboot.demospring.testlist.model.HouseInfoBean;

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
