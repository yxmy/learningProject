package com.yx.springboot.demospring.thread;

public class ThreadWait_notify {

    static ThreadWait_notify object = new ThreadWait_notify();

    public static class T1 extends Thread{
        @Override
        public void run(){
            synchronized (object){
                System.out.println("t1 get object start");
                try {
                    object.wait();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1 give up object");
            }
        }
    }

    public static class T2 extends Thread{
        @Override
        public void run(){
            synchronized (object){
                System.out.println("t2 get object start");
                try {
                    Thread.sleep(2000);
                    object.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 give up object");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        T1 t11 = new T1();
        T2 t2 = new T2();
        t1.start();t11.start();
        Thread.sleep(100);
        t2.start();
        t1.join();t11.join();t2.join();
//        AtomicInteger atomicInteger = new AtomicInteger(1);
//        int a = atomicInteger.getAndIncrement();
//        atomicInteger.compareAndSet(2, 3);
//        System.out.println(a + "--" + atomicInteger);
    }

}
