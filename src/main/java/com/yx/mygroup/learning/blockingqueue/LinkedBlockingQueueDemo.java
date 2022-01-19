package com.yx.mygroup.learning.blockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author yuanxin
 * @date 2021/12/7
 */
public class LinkedBlockingQueueDemo {

    public static void main(String[] args) {
        //有界队列
        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(3);

        //无界队列
//        LinkedBlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();

        new Thread(() -> {
            try {
                for (;;) {
//                    Thread.sleep(1000L);
                    blockingQueue.put(1);
                    System.out.println("向队列中放入：1");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (;;) {
                    Thread.sleep(1000L);
                    final Integer take = blockingQueue.take();
                    System.out.println("从队列中取出：" + take);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
