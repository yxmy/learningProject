package com.yx.springboot.demospring2.modelmapper.dto;

import com.yx.springboot.demospring2.modelmapper.model.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {
    String FirstName;
    String customerLastName;
    String addressStreet;
    String addressCity;
    String mark;
    Person person;
}
