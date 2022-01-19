package com.yx.mygroup.bpmcondition;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yuanxin
 * @date 2021/11/2
 */
@Getter
@Setter
public class BpmConditionMetadataDto {

    /**
     * 字段名
     */
    private String id;

    /**
     * 字段描述
     */
    private String name;

    /**
     * 字段类型，对应 DataType.NameConstants
     */
    private String type;

    /**
     * 组织类型，人员/部门/机构/岗位。。。/枚举
     */
    private String orgType;

    /**
     * 枚举ID
     */
    private String enumId;

}
