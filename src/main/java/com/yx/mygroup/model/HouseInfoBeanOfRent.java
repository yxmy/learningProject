package com.yx.mygroup.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseInfoBeanOfRent {

    /**
     * 总价
     */
    private String rentPrice;

    /**
     * 面积
     */
    private String coveredArea;

    /**
     * 户型
     */
    private String houseType;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 楼层
     */
    private String floor;

    /**
     * 朝向
     */
    private String orientation;

    /**
     * 装修
     */
    private String renovation;

    /**
     * 楼型
     */
    private String buildingType;

    /**
     * 出租方式
     */
    private String rentingStyle;

    /**
     * 商圈
     */
    private String tradingArea;

    /**
     * 配套设施
     */
    private String supportingFacilities;

    /**
     * 交通
     */
    private String Traffic;
}
