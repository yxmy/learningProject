package com.yx.springboot.demospring2.seamp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

import static java.util.concurrent.Executors.*;

public class SeampDemo implements Runnable {
    Semaphore semaphore = new Semaphore(5);
    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getId() + ": done!!!");
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService service = newFixedThreadPool(20);
        SeampDemo demo = new SeampDemo();
        for(int i=0; i < 20; i++){
            service.submit(demo);
        }
    }
}
