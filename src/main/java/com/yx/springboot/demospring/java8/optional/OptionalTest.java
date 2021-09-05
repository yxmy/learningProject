package com.yx.springboot.demospring.java8.optional;

import com.yx.springboot.demospring.testlist.model.User;
import org.junit.Test;

import java.util.Optional;

/**
 * @author Yuan_xin
 */
public class OptionalTest {

    @Test
    public void test1() {
        User user = new User();
        //of(T) T不能为空，不然会空指针异常
        Optional<User> user1 = Optional.of(user);
        //ofNullable(T) T可以为空
        Optional<User> user2 = Optional.ofNullable(user);

        Optional.ofNullable(user).orElseGet(User::new);
    }
}
