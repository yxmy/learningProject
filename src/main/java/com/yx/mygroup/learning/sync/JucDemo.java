package com.yx.mygroup.learning.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanxin
 * @date 2021/11/30
 */
public class JucDemo {

    public static void main(String[] args) {
        Test test = new Test();
        new Thread(() -> {
            final List<String> list = test.addList("a", "b");
            System.out.println(list);
        }).start();

        new Thread(() -> {
            final List<String> list = test.addList("c", "d");
            System.out.println(list);
        }).start();
    }

    static class Test {

        public List<String> addList(String name1, String name2) {
            List<String> list = new ArrayList<>();
            list.add(name1);
            list.add(name2);
            return list;
        }
    }

}
