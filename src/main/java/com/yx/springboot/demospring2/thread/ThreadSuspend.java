package com.yx.springboot.demospring2.thread;

public class ThreadSuspend {

    private static Object object = new Object();

    public static int i = 0;

    private static class ObjectThread extends Thread{
        public ObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run(){
            synchronized (object){
                System.out.println("thread: " + getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        ObjectThread thread1 = new ObjectThread("t1");
//        ObjectThread thread2 = new ObjectThread("t2");
//        thread1.start();
//        Thread.sleep(2000);
//        thread2.start();
//        Thread.sleep(2000);
//        thread1.resume();
//        thread2.resume();
        ///////////////////////////join///////////////////////////////
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int j =0; j < 1000; j++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(i);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
//        thread.join();
        System.out.println(i);
    }
}
