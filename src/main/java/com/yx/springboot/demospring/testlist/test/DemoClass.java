package com.yx.springboot.demospring.testlist.test;

import com.yx.springboot.demospring.testlist.model.UserDetail;
import com.yx.springboot.demospring.testlist.modelmapper.model.Customer;
import com.yx.springboot.demospring.testlist.modelmapper.model.Name;
import com.yx.springboot.demospring.testlist.modelmapper.model.SourceModel;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class DemoClass {

    public static int getFileCount(File file) {
        int count = 0;
        for (File file1 : file.listFiles()) {
            if (file1.isDirectory()) {
                count += getFileCount(file1);
            } else {
                count++;
            }
        }
        return count;
    }

    static class User {
        String name;
        int age;

        public User(String name, int age) {
            this.age = age;
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public UserDetail get() {
        com.yx.springboot.demospring.testlist.model.User user = new com.yx.springboot.demospring.testlist.model.User();
        user.setName("aaa");
        user.setAge(10);
        user.setAddress("北京市");
        return user;
    }

    @Test
    public void test() {
        UserDetail userDetail = get();
        System.out.println(userDetail);
    }

    @Test
    public void nullable() {
        SourceModel model = new SourceModel();
        String firstName = Optional.ofNullable(model).map(SourceModel::getCustomer).map(Customer::getName).map(Name::getFirstName).orElse("");
        System.out.println(firstName);
    }

    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add("poi");
        list.add("dsf");
        list.add("abc");
        list.add("oif");

        list.forEach(s -> {
            System.out.print(s + ",");
        });
        list.sort(null);
        System.out.println("----------------------------------");
        list.forEach(s -> {
            System.out.print(s + ",");
        });
    }

    @Test
    public void test3() {
        String name = "1231,444,88,9,";
        String substring = name.substring(0, name.lastIndexOf(","));
        System.out.println(substring);
    }

    @Test
    public void test4() {
        final String filePath = "C:\\aaa";
        File targetZip = new File(filePath);
        final StringBuilder command = new StringBuilder();
        command.append("7za a -tzip ")
                .append(targetZip.getAbsolutePath())
                .append(" ");
        try {
            final Process ps = Runtime.getRuntime().exec(command.toString());
            final BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            final StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            final String result = sb.toString();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        List<User> userList = new ArrayList<>();
        User user = new User("张三", 10);
        userList.add(user);
        String collect = userList.stream().map(User::getName).collect(Collectors.joining(","));
        System.out.println(collect);

        User user1 = new User("李四", 10);
        userList.add(user1);
        String collect1 = userList.stream().map(User::getName).collect(Collectors.joining(","));
        System.out.println(collect1);
    }

    @Test
    public void test6() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("、aaa");
        System.out.println(stringBuilder.delete(0, 1).toString());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add("a" + i);
        }
        List<String> strings;
        if (list.size() < 10) {
            strings = list.subList(0, list.size());
        } else {
            strings = list.subList(0, 10);
        }
        System.out.println(strings);
    }

    @Test
    public void test7() {
        List<String> list = new ArrayList<>();
        System.out.println(StringUtils.join(list, "、"));
        list.add("a");
        System.out.println(StringUtils.join(list, "、"));
        list.add("b");
        System.out.println(StringUtils.join(list, "、"));
    }

    @Test
    public void test8() {
        long baseTime = 200L;
        for (int i = 0; i < 10; i++) {
            double random = Math.random();
            long addRandomTime = Math.round(random * 800);
            System.out.print("第" + (i + 1) + "次随机数为：" + random + "；");
            System.out.println("随机时间为：" + (baseTime + addRandomTime) + "。");
        }
    }

    @Test
    public void test9() {
        String var1 = "123#332";
        String prefix = "123#";
        System.out.println(var1.substring(prefix.length()));
    }

    public static String convertMinuteTime(Integer totalMinute) {
        StringBuilder returnResult = new StringBuilder();
        int day;
        int hour;
        int minute = 0;
        day = totalMinute / (24 * 60);
        if (day > 0) {
            totalMinute -= (day * 24 * 60);
            returnResult.append(day).append("天");
        }
        hour = totalMinute / 60;
        if (hour > 0) {
            totalMinute -= (hour * 60);
            returnResult.append(hour).append("小时");
        }
        minute = totalMinute;
        if (minute > 0) {
            returnResult.append(minute).append("分钟");
        }
        return returnResult.toString();
    }

    public static void main(String[] args) {
        List<Long> varList1 = new ArrayList<>();
        varList1.add(1L);
        List<String> varList2 = new ArrayList<>();
        varList2.add("2");
        boolean b = compareListIncludeAny(varList1, varList2);
        System.out.println(b);
    }

    private static boolean compareListIncludeAny(List<?> varList1, List<?> varList2) {
        Set<?> varSet1 = new HashSet<>(varList1);
        Set<?> varSet2 = new HashSet<>(varList2);
        for (Object var : varSet2) {
            boolean contains = varSet1.contains(var);
            if (contains) {
                return true;
            }
        }
        return false;
    }

}