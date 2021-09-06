package com.yx.springboot.demospring.java8.optional;

import com.yx.springboot.demospring.java8.entity.Car;
import com.yx.springboot.demospring.java8.entity.Insurance;
import com.yx.springboot.demospring.java8.entity.OptionalCar;
import com.yx.springboot.demospring.java8.entity.OptionalPerson;
import com.yx.springboot.demospring.java8.entity.Person;
import com.yx.springboot.demospring.testlist.model.User;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

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

    @Test
    public void test2() throws Throwable {
        OptionalPerson optionalPerson = new OptionalPerson();
        final Optional<Optional<OptionalCar>> car = Optional.ofNullable(optionalPerson).map(OptionalPerson::getCar);
        final Optional<OptionalCar> car1 = Optional.ofNullable(optionalPerson).flatMap(OptionalPerson::getCar);
        final String unKnown = Optional.of(optionalPerson)
                .flatMap(OptionalPerson::getCar)
                .flatMap(OptionalCar::getInsurance)
                .map(Insurance::getName).orElse("unKnown");

        Person person = new Person();
        final String s = Optional.ofNullable(person).map(Person::getCar)
                .map(Car::getInsurance).map(Insurance::getName).orElse("unKnown");

        final String s1 = Optional.ofNullable(person).map(Person::getCar)
                .map(Car::getInsurance).map(Insurance::getName).orElseGet(() -> "unKnown");

        Optional.ofNullable(person).map(Person::getCar)
                .map(Car::getInsurance).map(Insurance::getName).orElseThrow((Supplier<Throwable>) RuntimeException::new);

    }
}
