package com.yx.mygroup.learning.java8.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

/**
 * https://blog.csdn.net/xktxoo/article/details/91380332
 */
public class DateTestDemo {

    /**
     * Instant
     */
    @Test
    public void test1(){
        //获取当前时间戳
        Instant instant = Instant.now();
        System.out.println(instant);
        //指定系统时间戳
        Instant instant1 = Instant.ofEpochMilli(System.currentTimeMillis());
        System.out.println(instant1);
        //解析指定时间戳
        Instant instant2 = Instant.parse("2019-10-23T14:27:00Z");
        System.out.println(instant2);
    }

    /**
     * LocalDateTime
     */
    @Test
    public void test2(){
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        //指定时间
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, 10, 23, 14, 33, 32, 123);
        System.out.println(localDateTime1);
        //解析时间
        LocalDateTime localDateTime2 = LocalDateTime.parse("2019-10-23T14:40:36");
        System.out.println(localDateTime2);
        LocalDateTime localDateTime3 = LocalDateTime.parse("2019-10-23 14:36:52", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(localDateTime3);
    }

    /**
     * ZonedDateTime
     */
    @Test
    public void test3(){
        //获取当前时区当前时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);
        //指定时间和时区
        ZonedDateTime zonedDateTime1 = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
        System.out.println(zonedDateTime1);
        //解析指定时间
        ZonedDateTime zonedDateTime2 = ZonedDateTime.parse("2019-10-23T14:51:35.954+08:00[Asia/Shanghai]", DateTimeFormatter.ISO_ZONED_DATE_TIME);
        System.out.println(zonedDateTime2);
    }

    /**
     * 时间对象转换
     */
    @Test
    public void test4(){
        Instant instant = Instant.now();
        //时间戳转LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println(localDateTime);
        //时间戳转时区时间
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println(zonedDateTime);
        //LocalDateTime转时间戳，因为LocalDateTime不带时区信息，因此需要指定当前时区到UTC的offset
        Instant instant1 = localDateTime.toInstant(ZoneOffset.ofHours(8));
        System.out.println(instant1);
        //时区时间转时间戳，ZonedDateTime自带时区信息
        Instant instant2 = zonedDateTime.toInstant();
        System.out.println(instant2);
        //LocalDateTime转时区时间（为时间加上时区信息）
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Tokyo"));
        System.out.println(zonedDateTime1);
        //时区时间转为LocalDateTime，将时区时间的时区信息去掉
        LocalDateTime localDateTime1 = zonedDateTime.toLocalDateTime();
        System.out.println(localDateTime1);
    }

    /**
     * LocalDateTime与Date转换
     */
    @Test
    public void test5(){
        Date date = Date.from(Instant.now());
        System.out.println(date);
        TimeZone.setDefault(TimeZone.getTimeZone("utc"));
        System.out.println(date);
        Instant instant = date.toInstant();
        System.out.println(instant);
    }

    /**
     * 时区转换
     */
    @Test
    public void test6(){
        //初始化北京时间
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);
        //北京时间转为UTC时间
        ZonedDateTime utcZonedDateTime = zonedDateTime.toInstant().atZone(ZoneId.of("UTC"));
        System.out.println(utcZonedDateTime);
        //初始化东京时间
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(2019, 10, 23, 15, 31, 59, 00, ZoneId.of("Asia/Tokyo"));
        System.out.println(zonedDateTime1);
        //东京时间转为芝加哥时间
        ZonedDateTime zonedDateTime2 = zonedDateTime1.toInstant().atZone(ZoneId.of("America/Chicago"));
        System.out.println(zonedDateTime2);
    }

    /**
     * 时间调整
     */
    @Test
    public void test7(){
        LocalDateTime localDateTime = LocalDateTime.now();
        //本年本月最后一天
        System.out.println(localDateTime.with(TemporalAdjusters.lastDayOfMonth()));
        //本年本月第一天
        System.out.println(localDateTime.with(TemporalAdjusters.firstDayOfMonth()));
        //本年下一个月的第一天
        System.out.println(localDateTime.with(TemporalAdjusters.firstDayOfNextMonth()));
        //下一年的第一天
        System.out.println(localDateTime.with(TemporalAdjusters.firstDayOfNextYear()));
        //本年最后一天
        System.out.println(localDateTime.with(TemporalAdjusters.lastDayOfYear()));
        //下一个周五
        System.out.println(localDateTime.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)));
        //本月的第一个周五
        System.out.println(localDateTime.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY)));
        //本月的最后一个周五
        System.out.println(localDateTime.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));
        //下一个周五，如果当前日期为周五，则返回当前时间
        System.out.println(localDateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)));
        //前一个周五
        System.out.println(localDateTime.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)));
        //前一个周五，如果当前时间为周五则返回当前时间
        System.out.println(localDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)));
        //当前日期加100天
        System.out.println(localDateTime.plusDays(100));
        //当前日期减100天
        System.out.println(localDateTime.minusDays(100));
    }

    /**
     * 计算日期时间差异
     */
    @Test
    public void test8(){
        //通过Period计算年龄
        LocalDate localDateTime = LocalDate.of(1994, 2, 16);
        LocalDate now = LocalDate.now();
        Period period = Period.between(localDateTime, now);
        System.out.println(period.getYears() + "--" + period.getMonths() + "--" + period.getDays());
        //计算时间差值
        Instant instant = Instant.now();
        Instant instant1 = instant.plus(Duration.ofMinutes(2));
        Duration duration = Duration.between(instant, instant1);
        System.out.println(duration.getSeconds());
        System.out.println(duration.toMillis());
        //按某一单位维度计算差值
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, 1, 1, 23, 0, 0);
        LocalDateTime localDateTime2 = LocalDateTime.now();
        Long dayDiff = ChronoUnit.DAYS.between(localDateTime1, localDateTime2);
        System.out.println(dayDiff);
        Long miniDiff = ChronoUnit.MINUTES.between(localDateTime1, localDateTime2);
        System.out.println(miniDiff);
    }

    /**
     * 时间打印
     */
    @Test
    public void test9(){
        LocalDateTime localDateTime = LocalDateTime.of(2019, 10, 23, 23, 10, 10);
        System.out.println(localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        LocalDateTime localDateTime1 = LocalDateTime.parse("20191023 20:00:00", DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
        System.out.println(localDateTime1);
    }

    /**
     * 判断是否为闰年
     */
    @Test
    public void test10(){
        LocalDate localDate = LocalDate.of(2000, 12, 20);
        System.out.println(localDate.isLeapYear());
    }

    /**
     * 判断前后
     */
    @Test
    public void test11(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = LocalDateTime.of(2019, 5, 20, 10, 10, 10);
        System.out.println(localDateTime.isAfter(localDateTime1));
        System.out.println(localDateTime.isBefore(localDateTime1));
    }

}
