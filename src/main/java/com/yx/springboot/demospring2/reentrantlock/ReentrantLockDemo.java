package com.yx.springboot.demospring2.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable {

    public static ReentrantLock reentrantLock = new ReentrantLock();
    static int j = 0;
    @Override
    public void run() {
        for(int i=0;i<1000000;i++){
            reentrantLock.lock();
            try{
                j++;
            }finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        ReentrantLockDemo demo1 = new ReentrantLockDemo();
        Thread thread = new Thread(demo);
        Thread thread1 = new Thread(demo1);
        thread.start();
        thread1.start();
        Thread.sleep(1000);
        thread.join();
        thread1.join();
        System.out.println(j);
    }
}
