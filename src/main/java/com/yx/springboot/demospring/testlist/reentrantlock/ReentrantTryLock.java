package com.yx.springboot.demospring.testlist.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantTryLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);
            }else{
                System.out.println(Thread.currentThread().getId() + ": get lock failure");
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ReentrantTryLock());
        Thread thread1 = new Thread(new ReentrantTryLock());
        thread.start();
        thread1.start();
    }
}
