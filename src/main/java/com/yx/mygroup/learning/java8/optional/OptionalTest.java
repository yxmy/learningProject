package com.yx.mygroup.learning.java8.optional;

import com.yx.mygroup.learning.java8.entity.Car;
import com.yx.mygroup.learning.java8.entity.OptionalCar;
import com.yx.mygroup.learning.java8.entity.OptionalPerson;
import com.yx.mygroup.learning.java8.entity.Person;
import com.yx.mygroup.model.User;
import com.yx.mygroup.learning.java8.entity.Insurance;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
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

    @Test
    public void test3() {
//        stringToInt("1").ifPresent(System.out::println);

        Map<String, String> params = new HashMap<>(6);
        params.put("a", "1");
        params.put("b", "a");
        params.put("c", "-1");
        params.put("d", "3");

        System.out.println(readDuration(params, "d"));
    }

    private Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private Integer readDuration(Map<String, String> map, String key) {
        return Optional.ofNullable(map.get(key)).flatMap(this::stringToInt).filter(i -> i > 1).orElse(0);
    }

    @Test
    public void test4() {
        LocalDateTime nowDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        LocalDate nowDate = nowDateTime.toLocalDate();
        LocalTime localTime = nowDateTime.toLocalTime();

        LocalDate now = LocalDate.now();
        Period between = Period.between(nowDate, now);
        System.out.println(between.getDays());

        Instant start = Instant.parse("2017-10-30T10:00:01.00Z");
        Instant end = Instant.parse("2017-10-30T12:00:31.00Z");

        Duration duration = Duration.between(start, end);
        duration.getSeconds();
        duration.getNano();

        LocalDate now1 = LocalDate.now();
        LocalDateTime dateTime = now1.atStartOfDay();
        System.out.println(dateTime);
        ZonedDateTime zonedDateTime = now1.atStartOfDay(ZoneId.systemDefault());
        System.out.println(zonedDateTime);
    }
}
