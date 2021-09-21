package com.yx.mygroup.learning.java8.streamapi;

import com.yx.mygroup.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream的中间操作
 *
 * @author Yuan_xin
 */
public class StreamApiTest1 {

    /**
     * 筛选与切片
     */
    @Test
    public void test() {
        List<User> userList = StreamApiTest.createUsers();

        //filter(Predicate p)
        userList.stream().filter(user -> user.getAge() > 20).forEach(System.out::println);

        //limit(n)
        userList.stream().limit(2).forEach(System.out::println);

        //skip(n)
        userList.stream().skip(20).forEach(System.out::println);

        //distinct()
        userList.stream().distinct().forEach(System.out::println);
    }

    /**
     * 映射
     */
    @Test
    public void test1() {
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        //map
        list.stream().map(String::toUpperCase).forEach(System.out::println);

        Stream<Stream<Character>> streamStream = list.stream().map(this::strToChar);
        //flatMap
        Stream<Character> characterStream = list.stream().flatMap(this::strToChar);
    }

    private Stream<Character> strToChar(String str) {
        List<Character> chars = new ArrayList<>();
        for (char c : str.toCharArray()) {
            chars.add(c);
        }
        return chars.stream();
    }

    /**
     * 排序
     */
    @Test
    public void test2() {
        //sorted()
        List<Integer> list = Arrays.asList(1, 2, 3, 1, 2, 67, 2, 43);
        list.stream().sorted().forEach(System.out::println);
        //sorted(Comparator c)
        List<User> userList = StreamApiTest.createUsers();
        userList.stream().sorted((user1, user2) -> Integer.compare(user1.getAge(), user2.getAge())).forEach(System.out::println);
    }

}
