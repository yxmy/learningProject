package com.yx.springboot.demospring.innerclass;

import java.util.HashMap;
import java.util.Map;

/**
 * map1是一个普通的HashMap实例，
 * 但map2是一个匿名类实例，只是该匿名类继承自HashMap。
 * map3也是一个继承自HashMap的匿名类实例，并且添加了static代码块来初始化数据。
 * 观察编译输出可发现Main$1.class和Main$2.class两个匿名类文件。
 *
 * @author yuanxin
 * @date 2021/8/25
 */
public class Outer2 {

    public static void main(String[] args) {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<String, Object>() {
        };
        Map<String, Object> map3 = new HashMap<String, Object>() {
            {
                put("A", 1);
                put("B", 2);
            }
        };
    }

}
