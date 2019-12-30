package com.yx.springboot.demospring.mybatis.provider;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 主要用途：根据复杂的业务来动态生成SQL
 * 目标：使用Java工具类来代替传统的XML文件。（例如：UserSqlProvider->UserMapper.xml）
 * @author yx
 */
public class UserSqlProvider {

    /**
     * 方式1：在工具类的方法中，可以手动编写SQL
     * @param username username
     * @return 字符串
     */
    public String listByUsername(String username){
        return "select * from t_user where username = #{" + username + "}";
    }

    /**
     * 方式2：也可以根据官方提供的API来编写动态SQL。
     * @param username username
     * @param password password
     * @return 字符串
     */
    public String getUser(@Param("username") String username, @Param("password") String password){
        return new SQL(){{
            SELECT("*");
            FROM("t_user");
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
                WHERE("username like #{username} and password like #{password}");
            } else {
                WHERE("1=2");
            }
        }}.toString();
    }
}
