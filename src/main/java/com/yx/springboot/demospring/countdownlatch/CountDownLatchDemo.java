package com.yx.springboot.demospring.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo implements Runnable {
    static final CountDownLatch end = new CountDownLatch(10);
    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println(Thread.currentThread().getId() + ": check complete");
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            end.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pools = Executors.newFixedThreadPool(10);
        CountDownLatchDemo latchDemo = new CountDownLatchDemo();
        for(int i=0;i<10; i++){
            pools.execute(latchDemo);
        }
        end.await();
        System.out.println("all complete!!!");
        pools.shutdown();
    }
}
