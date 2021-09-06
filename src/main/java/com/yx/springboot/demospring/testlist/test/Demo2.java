package com.yx.springboot.demospring.testlist.test;

import com.yx.springboot.demospring.interfaces.Hello;
import com.yx.springboot.demospring.interfaces.Hello2;
import com.yx.springboot.demospring.interfaces.HelloImpl;
import com.yx.springboot.demospring.testlist.enums.FilmPublishType;
import com.yx.springboot.demospring.testlist.model.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TimeZone;
import java.util.TreeMap;

public class Demo2 extends HashMap<String, Object> {

    public List<String> list = new ArrayList<>();

    public void test1() {
        double a = 1.0;
        double b = 3.0;
        double c = -4.0;
        // 求平方根可用 Math.sqrt():
        // System.out.println(Math.sqrt(2)); ==> 1.414
        // TODO:
        double r1 = 0;
        double r2 = 0;
        double sqrt = Math.sqrt(b * b - (4.0 * a * c));
        r1 = (-b + sqrt) / 2.0 * a;
        r2 = (-b - sqrt) / 2.0 * a;

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r1 == 1 && r2 == -4 ? "测试通过" : "测试失败");
    }

    @Test
    public void test2() {
        String expression = "11111";
        char a = expression.charAt(0);
        System.out.println(a >= '0');
        System.out.println(a >= 'r');
    }

    public void test3() {
        int[][] deepArray = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8}
        };
        System.out.println(Arrays.deepToString(deepArray));
    }

    public void test4() {
        try {
            Class clazz = Class.forName("java.util.String");
            Field[] fields = clazz.getFields();
            fields[0].setAccessible(true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                System.out.println(method.getName());
                if (method.getName().equals("sayHello")) {
                    System.out.println("Hello, " + args[0]);
                }
                if (method.getName().equals("sayHello2")) {
                    System.out.println("Hello2, " + args[0]);
                }
                return null;
            }
        };
        Hello2 hello = (Hello2) Proxy.newProxyInstance(Hello2.class.getClassLoader(), new Class[]{Hello2.class}, handler);
        String add = hello.add();
        System.out.println(add);
        hello.sayHello("袁鑫");
        hello.sayHello2("袁鑫");
    }

    public void test6() {
        Class<Demo2> demo2Class = Demo2.class;
        Type type = demo2Class.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                System.out.println(actualTypeArgument);
            }
        }
    }

    public void test7() {
        List<String> list = new ArrayList<>();
        list.add("a");
        Object[] objects = list.toArray();
        String[] strings = list.toArray(new String[0]);
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public void test8() {
        Map<FilmPublishType, Object> map = new EnumMap<>(FilmPublishType.class);
        map.put(FilmPublishType.cooperation, 111);
        map.put(FilmPublishType.alone, 222);
    }

    public void test9() {
        Map<User, Integer> map = new TreeMap<>((o1, o2) -> Integer.compare(o2.getAge(), o1.getAge()));

        Queue<User> queue = new PriorityQueue<>(new UserComparator());
        queue.offer(new User("张三", 14));
        queue.offer(new User("李四", 12));
        queue.offer(new User("张三", 17));
        System.out.println(queue.peek() == null ? null : queue.poll().toString());
        System.out.println(queue.peek() == null ? null : queue.poll().toString());
        System.out.println(queue.peek() == null ? null : queue.poll().toString());
        System.out.println(queue.peek() == null ? null : queue.poll().toString());
    }

    static class UserComparator implements Comparator<User> {

        @Override
        public int compare(User o1, User o2) {
            if (o1.getAge() == o2.getAge()) {
                return 0;
            }
            return Integer.compare(o1.getAge(), o2.getAge());
        }
    }

    public void test10() {
        Deque<String> deque = new LinkedList<>();
        deque.offerLast("A");
        deque.offerLast("B");
        deque.offerFirst("C");
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollFirst());
    }

    public void test11() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println("洗牌前：" + list);
        Collections.shuffle(list);
        System.out.println("洗牌后：" + list);
    }

    public void test12() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        System.out.println(localDate);
        System.out.println(localDateTime.toLocalDate());
        System.out.println(localTime);
        System.out.println(localDateTime.toLocalTime());
        System.out.println(localDateTime);

        final LocalDate of1 = LocalDate.of(2021, 9, 1);
        System.out.println(of1);
        final LocalTime of2 = LocalTime.of(12, 54); //如果分钟数大于59 或者小于0 报错
        System.out.println(of2);
        final LocalDateTime of3 = LocalDateTime.of(2022, 2, 3, 4, 3, 2);
        System.out.println(of3);

        LocalDateTime dt = LocalDateTime.parse("2019-11-19T15:16:17");
        LocalDate d = LocalDate.parse("2019-11-19");
        LocalTime t = LocalTime.parse("15:16:17");

    }

    public void test13() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dtf.format(LocalDateTime.now()));

        final LocalDateTime parse = LocalDateTime.parse("2021-01-01T09:00:00");
        System.out.println(parse);

        final LocalDateTime parse1 = LocalDateTime.parse("2021-01-01 09:00:00", dtf);
        System.out.println(parse1);
    }

    public void test14() {
        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime localDateTime = now.plusDays(4).minusHours(2);
        System.out.println(localDateTime);

        //调整日期，
        final LocalDateTime localDateTime1 = now.withDayOfMonth(3).withHour(23).withSecond(23);
        System.out.println(localDateTime1);

        //下月第1天:
        final LocalDateTime with1 = LocalDateTime.now().with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println(with1);

        //本月第一个周一
        final LocalDateTime with = LocalDateTime.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        System.out.println(with);

    }

    public void test15() {
        LocalDateTime dateTime1 = LocalDateTime.of(1994, 3, 26, 0, 0, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(1994, 2, 16, 3, 45, 35);

        Duration duration = Duration.between(dateTime1, dateTime2);
        System.out.println(duration);

        Period until = LocalDate.now().until(LocalDate.of(1994, 3, 26));
        System.out.println(until);

    }

    public void test16() {
        //默认时区
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        //指定时区
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now(ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime);
        System.out.println(zonedDateTime1);

        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime2 = dateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTime3 = dateTime.atZone(ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime2);
        System.out.println(zonedDateTime3);
    }

    public void test17() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.systemDefault());
        System.out.println(zdt);
        ZonedDateTime zonedDateTime = zdt.withZoneSameInstant(ZoneId.of("America/New_York"));
        System.out.println(zonedDateTime);

        LocalDateTime dateTime = zdt.toLocalDateTime();
        System.out.println(dateTime);
    }

    public void test18() {
        Instant instant = Instant.now();
        System.out.println(instant.getEpochSecond());
        System.out.println(instant.getNano());

        Instant instant1 = Instant.ofEpochSecond(1568568760);
        ZonedDateTime zdt = instant1.atZone(ZoneId.systemDefault());
        System.out.println(zdt);
    }

    public void test19() {
        Instant ins = new Date().toInstant();
        ZonedDateTime zonedDateTime = ins.atZone(ZoneId.systemDefault());

        ZonedDateTime zdt = ZonedDateTime.now();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeZone(TimeZone.getTimeZone(zdt.getZone().getId()));
        calendar.setTimeInMillis(zdt.toEpochSecond() * 1000);
        System.out.println(calendar.toString());
    }

    public void test20() {
        double b = Math.sqrt(100.0) % 1.0;
        System.out.println(b);
        double b1 = Math.sqrt(100.1) % 1;
        System.out.println(b1);

        double b2 = Math.sqrt(100.0) % 1.0;
        System.out.println(b2);
        double b3 = Math.sqrt(100.1) % 1.0;
        System.out.println(b3);
    }

    public void test21() {
        Map<String, Object> map = new HashMap<>(16);
        map.entrySet().stream().sorted(Entry.comparingByKey()).forEachOrdered(System.out::println);

        map.getOrDefault("defaultKey", 100);
        map.computeIfAbsent("ifAbsent", name -> new ArrayList<>().add(name));
        map.computeIfPresent("ifPresent", (String name, Object user) -> user);
    }

    @Test
    public void test22() {
        Hello hello = new HelloImpl();
        System.out.println(hello.add());
        hello.sayHello("aaa");
    }

}
