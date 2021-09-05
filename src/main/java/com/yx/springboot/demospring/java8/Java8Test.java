package com.yx.springboot.demospring.java8;


import com.yx.springboot.demospring.testlist.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Yuan_xin
 */
@Slf4j
public class Java8Test {

    @Test
    public void test1() {
        List<User> users = createUsers();
        //先按照用户名字排序，如果名字一样，再按照年龄排序
        users.sort(Comparator.comparing(User::getName).reversed().thenComparing(User::getAge));
        System.out.println(users);
    }

    @Test
    public void test2() {
        Predicate<Integer> predicate = ((Integer integer) -> integer < 10);
        Predicate<Integer> negate = predicate.negate();
        Predicate<Integer> or = negate.and((integer) -> integer < 20).or((integer) -> integer < 30);
    }

    @Test
    public void test3() {
        String [] hello = new String[] {"hello", "world"};
        List<String[]> collect = Arrays.stream(hello).map(str -> str.split("")).distinct().collect(Collectors.toList());
        System.out.println(collect);

        Stream<String[]> stream = Arrays.stream(hello).map(str -> str.split(""));
        List<String> collect1 = Arrays.stream(hello).map(str -> str.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        System.out.println(collect1);
    }

    @Test
    public void test4() {
        List<List<String>> mainList = new ArrayList<>();
        List<String> subList1 = Arrays.asList("a", "b", "c");
        List<String> subList2 = Arrays.asList("a", "b", "d");
        mainList.add(subList1);
        mainList.add(subList2);
        List<String> collect = mainList.stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test5() {
        List<User> users = createUsers();
        users.stream().mapToInt(User::getAge).sum();

        //不包含100
        IntStream.range(0, 100).filter(i -> i % 2 == 0).forEach(System.out::println);
        //包含100
        IntStream.rangeClosed(0, 100).filter(i -> i % 2 == 0).forEach(System.out::println);

        //生成勾股定理的数组
        Stream<int[]> stream = IntStream.rangeClosed(1, 100).boxed().flatMap(a -> IntStream.range(a, 100)
                .peek(x -> log.info("x is " + x))
                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));
        stream.limit(5).forEach(t ->
                System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        Stream<User> userStream = Stream.of(users.get(0)).filter(user -> user.getAge() > 10);

        Optional<User> maxAgeUser = users.stream().max(Comparator.comparingInt(User::getAge));
        Integer collect = users.stream().collect(Collectors.summingInt(User::getAge));

        IntSummaryStatistics summaryStatistics = users.stream().collect(Collectors.summarizingInt(User::getAge));
        summaryStatistics.getSum();
        summaryStatistics.getMax();
        summaryStatistics.getMin();
        summaryStatistics.getAverage();
        summaryStatistics.getCount();

        users.stream().collect(Collectors.reducing(0, User::getAge, (i, j) -> i + j));

        long sequentialStart = System.currentTimeMillis();
        Map<Boolean, List<User>> collect1 = users.stream().sequential().collect(Collectors.partitioningBy(user -> user.getAge() > 20));
        System.out.println("sequential time --->" + (System.currentTimeMillis() - sequentialStart));

        long parallelStart = System.currentTimeMillis();
        Map<Boolean, List<User>> collect3 = users.stream().parallel().collect(Collectors.partitioningBy(user -> user.getAge() > 20));
        System.out.println("parallelStart time --->" + (System.currentTimeMillis() - parallelStart));

        Map<Boolean, User> collect2 = users.stream().collect(Collectors.partitioningBy(user -> user.getAge() > 24, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(User::getAge)), Optional::get)));
    }









    public List<User> createUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User("张三", 22);
        User user2 = new User("张三", 23);
        User user3 = new User("王五", 24);
        User user4 = new User("马六", 25);
        User user5 = new User("赵七", 26);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        return users;
    }
}
