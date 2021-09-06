package com.yx.springboot.demospring.java8.entity;

import lombok.Getter;

import java.util.Optional;

/**
 * @author yuanxin
 * @date 2021/9/6
 */
@Getter
public class OptionalCar {

    private Optional<Insurance> insurance;
}
