package com.yx.springboot.demospring.testlist.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {

    static class Candidate{
        int id;
        volatile int score;
    }

    public static final AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    public static AtomicInteger allScore = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        final Candidate candidate = new Candidate();
        Thread [] threads = new Thread[10000];
        for(int i = 0; i < 10000; i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(Math.random() > 0.4){
                        scoreUpdater.getAndIncrement(candidate);
                        allScore.getAndIncrement();
                    }
                }
            });
            threads[i].start();
        }
        for(int i=0;i<10000;i++){
            threads[i].join();
        }
        System.out.println(candidate.score);
        System.out.println(allScore);
    }
}
