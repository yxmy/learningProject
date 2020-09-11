package com.yx.springboot.demospring.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Demo4 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (1 == 1){

                }
            }
        });
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
            }
        });
    }
}
