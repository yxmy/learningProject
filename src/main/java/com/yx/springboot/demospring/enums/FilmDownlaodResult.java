package com.yx.springboot.demospring.enums;

/**
 * 下载状态编码：请求下载
 *
 * @author Jiajinfang
 */
public enum FilmDownlaodResult {
    /**
     * 请求下载
     */
    requestDown,
    /**
     * 成功
     */
    success,
    /**
     * 没有权限
     */
    noPermission,
    /**
     * 没有查询结果
     */
    noQueryResult,
    /**
     * 未知错误
     */
    unknownError

}
