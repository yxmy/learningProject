package com.yx.springboot.demospring2.modelmapper.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SourceModel {
    Customer customer;
    Address address;
    Person person;
    String mark;
}
