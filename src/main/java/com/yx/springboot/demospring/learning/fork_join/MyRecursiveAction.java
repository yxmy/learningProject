package com.yx.springboot.demospring.learning.fork_join;

import com.mysql.cj.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * RecursiveAction是一个动作，没有返回值的时候使用这个
 *
 * @author yuanxin
 * @date 2021/9/7
 */
@Slf4j
public class MyRecursiveAction extends RecursiveAction {

    public static void main(String[] args) throws InterruptedException {
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction();
        myRecursiveAction.resource = new ArrayList<String>(){{
            add("a");
            add("b");
            add("c");
            add("d");
            add("e");
        }};
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(myRecursiveAction);

        //fork-join 开启异步执行，所以必须给出一定的cpu调用的时间，否则主线程结束，任务来不及开启，方法总是返回false
        final boolean success = pool.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println(success);
    }

    private List<String> resource = new ArrayList<>();

    @Override
    protected void compute() {
        if (resource.size() <= 2) {
            log.info("进来了，当前resource的大小为:" + resource.size());
            resource.forEach(System.out::println);
        } else {
            MyRecursiveAction lMyRecursiveAction = new MyRecursiveAction();
            MyRecursiveAction rMyRecursiveAction = new MyRecursiveAction();
            lMyRecursiveAction.resource = resource.subList(0, resource.size() / 2);
            rMyRecursiveAction.resource = resource.subList(resource.size() / 2, resource.size());
            lMyRecursiveAction.fork();
            rMyRecursiveAction.fork();
        }
    }
}
