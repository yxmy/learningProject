package com.yx.mygroup.learning.java8.streamapi;

import com.yx.mygroup.model.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Stream的终止操作
 *
 * @author Yuan_xin
 */
public class StreamApiTest2 {

    /**
     * 匹配与查找
     */
    @Test
    public void test1() {
        List<User> users = StreamApiTest.createUsers();
        //allMatch(Predicate p)
        boolean allMatch = users.stream().allMatch(user -> user.getAge() > 10);
        //anyMatch(Predicate p)
        boolean anyMatch = users.stream().anyMatch(user -> user.getAge() > 10);
        //noneMatch(Predicate p)
        boolean noneMatch = users.stream().noneMatch(user -> user.getName().contains("袁"));
        //findFirst()
        Optional<User> firstUser = users.stream().findFirst();
        //findAny()
        Optional<User> anyUser = users.stream().findAny();
        //count()
        long count = users.stream().filter(user -> user.getAge() >18).count();
        //max(Comparator c)
        Optional<Integer> max = users.stream().map(User::getAge).max(Integer::compare);
        //min(Comparator c)
        Optional<User> min = users.stream().min((user1, user2) -> Integer.compare(user1.getAge(), user2.getAge()));
    }

    /**
     * 归约
     */
    @Test
    public void test2() {
        //reduce(T idea, BinaryOperator b)   计算1到10的和
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer reduce = list.stream().reduce(0, Integer::sum);
        //reduce(BinaryOperator b)   计算所有用户年龄的和
        List<User> users = StreamApiTest.createUsers();
        Optional<Integer> reduce1 = users.stream().map(User::getAge).reduce(Integer::sum);
    }

    /**
     * 收集
     */
    @Test
    public void test3() {
        List<User> users = StreamApiTest.createUsers();
        List<String> names = users.stream().map(User::getName).collect(Collectors.toList());
        Set<Integer> ages = users.stream().map(User::getAge).collect(Collectors.toSet());
    }

}
