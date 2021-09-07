package com.yx.springboot.demospring.learning.thread;

public class ThreadPriority {

    public static class HighPriority extends Thread{
        static int count = 0;
        @Override
        public void run(){
            while (true){
                synchronized (ThreadPriority.class){
                    count++;
                    if(count > 1000000){
                        System.out.println("high priority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread{
        static int count = 0;
        @Override
        public void run(){
            while (true){
                synchronized (ThreadPriority.class){
                    count++;
                    if(count > 1000000){
                        System.out.println("low priority is complete");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HighPriority highPriority = new HighPriority();
        LowPriority lowPriority = new LowPriority();
        highPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.setPriority(Thread.MIN_PRIORITY);
        lowPriority.start();
        highPriority.start();
    }
}
