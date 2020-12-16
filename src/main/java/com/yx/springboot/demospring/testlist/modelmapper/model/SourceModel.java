package com.yx.springboot.demospring.testlist.modelmapper.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class SourceModel {
    Customer customer;
    Address address;
    Person person;
    String mark;

    public Optional<Customer> getCustomer1(){
        return Optional.ofNullable(customer);
    }
}
