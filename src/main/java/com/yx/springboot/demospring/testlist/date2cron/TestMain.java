package com.yx.springboot.demospring.testlist.date2cron;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yuanxin
 * @date 2021/1/13
 */
public class TestMain {

    public static void main(String[] args) {
        String cron = Date2CronUtil.getCron(new Date());
        System.out.println(cron);

        Date date = Date2CronUtil.getCronToDate(cron);
        System.out.println(date);

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i + "", i + "");
        }
        map.keySet().removeIf(s -> "0".equals(s) || "1".equals(s) || "2".equals(s));
        System.out.println(map);
    }
}
