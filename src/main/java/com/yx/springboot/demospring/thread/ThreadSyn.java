package com.yx.springboot.demospring.thread;

public class ThreadSyn implements Runnable {

    static ThreadSyn threadSyn = new ThreadSyn();
    static int count = 0;


//    public void run(){
//        synchronized (threadSyn){
//            for(int i = 0; i < 1000000; i++){
//                count++;
//            }
//        }
//    }
    public static synchronized void increase(){
        count++;
    }
    @Override
    public void run(){
        for(int i=0;i<100000;i++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread threadSyn0 = new Thread(new ThreadSyn());
        Thread threadSyn1 = new Thread(new ThreadSyn());
        threadSyn0.start();threadSyn1.start();
        threadSyn0.join();threadSyn1.join();
        System.out.println(count);
    }

}
