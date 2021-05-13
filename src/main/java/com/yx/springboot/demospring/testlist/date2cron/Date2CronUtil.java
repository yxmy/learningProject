package com.yx.springboot.demospring.testlist.date2cron;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuanxin
 * @date 2021/1/13
 */
public class Date2CronUtil {

    /**
     * 日期转化为cron表达式
     * @param date
     * @return
     */
    public static String getCron(Date date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return fmtDateToStr(date, dateFormat);
    }

    /**
     * cron表达式转为日期
     * @param cron
     * @return
     */
    public static Date getCronToDate(String cron) {
        String dateFormat="ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     * Description:格式化日期,String字符串转化为Date
     *
     * @param date
     * @param dtFormat
     *            例如:yyyy-MM-dd HH:mm:ss yyyyMMdd
     * @return
     */
    public static String fmtDateToStr(Date date, String dtFormat) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
