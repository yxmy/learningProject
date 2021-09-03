package com.yx.springboot.demospring.java8.streamapi;

import com.yx.springboot.demospring.testlist.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Yuan_xin
 */
public class StreamApiTest {

    /**
     * 通过集合
     */
    @Test
    public void test1() {
        List<User> users = createUsers();
        //返回一个顺序流
        Stream<User> stream = users.stream();
        //返回一个并行流
        Stream<User> parallelStream = users.parallelStream();
    }

    /**
     * 通过数组
     */
    @Test
    public void test2() {
        int [] intArr = new int[] {1, 2, 3};
        IntStream intStream = Arrays.stream(intArr);

        User user1 = new User();
        User user2 = new User();
        User [] userArr = new User[] {user1, user2};
        Stream<User> userStream = Arrays.stream(userArr);
    }

    /**
     * 通过Stream.of
     */
    @Test
    public void test3() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4);
    }

    /**
     * 创建无限流
     */
    @Test
    public void test4() {
        //迭代(获取前十个偶数)
        Stream.iterate(0, t -> t+2).limit(10).forEach(System.out::println);

        //生成(生成10个随机数)
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User("张三", 22);
        User user2 = new User("李四", 23);
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
