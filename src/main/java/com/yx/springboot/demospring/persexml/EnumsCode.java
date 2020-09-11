/**
 * $Author: wuym $
 * $Rev: 322 $
 * $Date:: 2012-08-06 10:38:42#$:
 *
 * Copyright (C) 2012 Seeyon, Inc. All rights reserved.
 *
 * This software is the proprietary information of Seeyon, Inc.
 * Use is subject to license terms.
 */
package com.yx.springboot.demospring.persexml;

/**
 * <p>Title: T1开发框架</p>
 * <p>Description: java枚举组件接口定义，实现了该接口的枚举类，可以利用前端框架的codeCfg枚举展现组件
 * 进行诸如枚举项显示、枚举值转换等操作。该接口定义了前端codeCfg组件所必须的两个信息：值和显示文本</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: seeyon.com</p>
 * @since CTP2.0
 */
public interface EnumsCode {

    /**
     * 返回枚举值，用于枚举在前端页面进行选取和存储
     * 
     * @return 枚举值
     */
    public String getValue();

    /**
     * 返回枚举显示文本，用于前端页面显示
     * 
     * @return 枚举显示文本
     */
    public String getText();
}