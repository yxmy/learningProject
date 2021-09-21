package com.yx.mygroup.learning.java8.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * @author yuanxin
 * @date 2021/9/6
 */
@Getter
@Setter
public class OptionalPerson {

    private Optional<OptionalCar> car;
}
