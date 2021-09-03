package com.yx.springboot.demospring.java8.streamapi;

import com.yx.springboot.demospring.testlist.model.User;
import org.junit.Test;

import java.util.List;

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

}
