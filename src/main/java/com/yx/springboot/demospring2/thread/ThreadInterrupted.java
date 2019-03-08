package com.yx.springboot.demospring2.thread;

public class ThreadInterrupted {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Thread Interrupted");
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupt when sleep");
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Interrupt when sleep");
        }
        System.out.println("调用interrupt方法!");
        thread.interrupt();
    }
}
