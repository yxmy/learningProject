package com.yx.springboot.demospring.testlist.test;

import com.yx.springboot.demospring.testlist.mybatis.model.User;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Demo3 {

    @Test
    public void test() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        LocalDateTime lastDay = localDateTime.minusDays(1);
        System.out.println(lastDay);
    }

    @Test
    public void test2() {
        LocalDateTime localDate = LocalDateTime.now();
        System.out.println(localDate.getYear());
        System.out.println(localDate.getMonth().getValue());
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getHour());
        System.out.println(localDate.getMinute());
        System.out.println(localDate.getSecond());
    }

    /**
     * 文件复制
     */
    @Test
    public void test3() throws IOException {
        String oldPath = "E:\\working&&learning\\学习相关\\JAVA_学习文件\\Java成神之路\\Java面试\\06-面试\\JAVA笔试题及面试题\\jsd1209考试.doc";
        String newPath = "E:\\jsd1209考试.doc";
        int byteRead = 0;
        File file = new File(oldPath);
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(newPath)) {
                    byte[] bytes = new byte[1024];
                    while ((byteRead = fileInputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, byteRead);
                    }
                }
            }
        }
        System.out.println("success!!");
    }

    @Test
    public final void test4() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put(null, "null");
        Map<String, String> map = new HashMap<>(2);
        map.put(null, null);
    }

    @Test
    public void test5() {
        System.out.println(getNum(1));
    }

    public int getNum(int index) {
        if (index < 0) {
            throw new RuntimeException("坐标不能为负数");
        }
        if (index == 1 || index == 2) {
            return 1;
        } else {
            return getNum(index - 1) + getNum(index - 2);
        }
    }

    @Test
    public void test6() {
        short s1 = 1;
//        s1 = s1 + 1;  编译报错，需要强制类型转换
        s1 += 1;
        System.out.println(s1);
    }

    @Test
    public void test7() {
        Integer i = new Integer(0);
        i = add(i);
        System.out.println(i.intValue());

    }

    public Integer add(Integer i) {
        int val = i.intValue();
        val += 3;
        i = new Integer(val);
        return i;
    }

    @Test
    public void test8() {
        File file = new File("C:\\Users\\Yuan_xin\\Desktop");
        File[] files = file.listFiles();
        for (File file1 : files) {
            String fileName = file1.getName();
            if (fileName.contains(".doc")) {
                System.out.println(fileName);
            }

        }
    }

    public boolean test9() throws IOException {
        File file1 = new File("");
        File file2 = new File("");
        byte[] buffer1 = new byte[1024];
        byte[] buffer2 = new byte[1024];
        try (FileInputStream fileInputStream1 = new FileInputStream(file1)) {
            try (FileInputStream fileInputStream2 = new FileInputStream(file2)) {
                if (fileInputStream1.available() != fileInputStream2.available()) {
                    return false;
                }
                while (fileInputStream1.read() != -1 && fileInputStream2.read() != -1) {
                    if (fileInputStream1.read() != fileInputStream2.read()) {
                        return false; //不一致
                    }
                }
            }
        }
        return true;

    }

    @Test
    public void test10() {
        StringBuilder str = new StringBuilder("a");
        for (int i = 0; i < 10000000; i++) {
            str.append(i);
        }
        System.out.println(str.length());
    }

    @Test
    public void test11() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "1");

    }

    @Test
    public void test12() {
        System.out.println(lastBoxEggNum(8));
    }

    public int lastBoxEggNum(int eggNum) {
        if (eggNum <= 0) {
            return 0;
        }
        for (int i = 0; ; i++) {
            //每次计算盒子中应该放多少鸡蛋，2的幂次方
            int boxCapacity = (int) Math.pow(2, i);
            //如果剩余鸡蛋数量大于盒子中应该放的数量，则新增一个盒子，直到小于等于为止
            if (eggNum > boxCapacity) {
                eggNum = eggNum - boxCapacity;
            } else {
                break;
            }
        }
        return eggNum;
    }

    @Test
    public void test13() {
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID());
        }
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setUserId("111");
        list.add(user);
        user.setUserId("@22");
        System.out.println(list.toString());
    }


    // 学校数据
    @Getter
    class school {
        int shoolid;
        String schoolname; //学校名字
    }

    //上课数据
    @Getter
    class lesson {
        int schoolid;
        int studentCount; //学生数量
    }

    @Test
    public void test14() {
        System.out.println(java.util.UUID.randomUUID().toString());
    }

    @Test
    public void test15() {
        List<String> strings = new ArrayList<>();
        strings.add("111");
        strings.add("222");
        strings.add("333");
        System.out.println(strings);
        strings.remove("111");
        System.out.println(strings);
    }

    @Test
    public void test16() throws Exception {
        User user = new User();
        user.setUserId("111");
        Method method = User.class.getMethod("getUserId");
        String userId = (String) method.invoke(user);
        System.out.println(userId);
        Method setUsername = User.class.getMethod("setUsername", String.class);
        setUsername.invoke(user, "小明");
        System.out.println(user.getUsername());
    }

    @Test
    public void test17() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        System.out.println(StringUtils.join(list));
        System.out.println(StringUtils.joinWith(",", list));

    }

}
