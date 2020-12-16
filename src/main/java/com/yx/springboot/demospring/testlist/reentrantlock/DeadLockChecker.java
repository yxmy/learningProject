package com.yx.springboot.demospring.testlist.reentrantlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeadLockChecker implements Runnable{

    public static final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    @Override
    public void run() {
        while (true){
            long [] threadId = threadMXBean.findDeadlockedThreads();
            if(threadId != null){
                ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadId);
                for(Thread thread : Thread.getAllStackTraces().keySet()){
                    for(int i=0;i<threadInfos.length;i++){
                        if(thread.getId() == threadInfos[i].getThreadId()){
                            thread.interrupt();
                        }
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void check(){
        Thread thread = new Thread(new DeadLockChecker());
        thread.setDaemon(true);
        thread.start();
    }
}
