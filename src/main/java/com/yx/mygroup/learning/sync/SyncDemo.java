package com.yx.mygroup.learning.sync;

/**
 * @author yuanxin
 * @date 2021/11/25
 */
public class SyncDemo {

    public static synchronized void test1() {
        System.out.println("aaa");
    }

    public synchronized void test2() {
        System.out.println("aaa");
    }

    public void test3() {

        synchronized (this) {

        }
    }

    public void test4() {
        synchronized (Object.class) {

        }
    }

    public void test5() {
        String lock = "";
        synchronized (lock) {

        }
    }
}
