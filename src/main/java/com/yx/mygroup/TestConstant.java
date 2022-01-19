package com.yx.mygroup;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanxin
 * @date 2021/10/12
 */
public class TestConstant {

    public static Map<String, String> map = new HashMap<>();

    static {
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
    }
}
