package com.yx.springboot.demospring.learning.fork_join;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * 如果有返回值，叫任务
 *
 * 找出一个数组中所有的偶数
 *
 * @author yuanxin
 * @date 2021/9/7
 */
public class MyRecursiveTask extends RecursiveTask<Integer> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final Integer [] integers = IntStream.generate(() -> new Random().nextInt(100))
                .limit(102).boxed().toArray(Integer[]::new);

        MyRecursiveTask myRecursiveTask = new MyRecursiveTask();
        myRecursiveTask.arrays = integers;
        ForkJoinPool pool = new ForkJoinPool();
        Future<Integer> feature = pool.submit(myRecursiveTask);

        System.out.println(feature.get());
        System.out.println(myRecursiveTask.count);
        System.out.println(myRecursiveTask.join());

    }

    private Integer [] arrays;
    private Integer maxlength = 10;
    int count = 0;

    @Override
    protected Integer compute() {
        if (arrays.length <= maxlength) {
            count = (int) Arrays.stream(arrays).filter(i -> i % 2 == 0).count();
        } else {
            MyRecursiveTask lMyRecursiveTask = new MyRecursiveTask();
            MyRecursiveTask rMyRecursiveTask = new MyRecursiveTask();
            lMyRecursiveTask.arrays = Arrays.copyOfRange(arrays, 0, arrays.length / 2);
            rMyRecursiveTask.arrays = Arrays.copyOfRange(arrays, arrays.length / 2, arrays.length);
            lMyRecursiveTask.fork();
            rMyRecursiveTask.fork();
            final Integer lJoin = lMyRecursiveTask.join();
            final Integer rJoin = rMyRecursiveTask.join();
            count = lJoin + rJoin;
        }
        return count;
    }
}
