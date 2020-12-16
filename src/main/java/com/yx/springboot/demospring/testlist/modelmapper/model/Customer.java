package com.yx.springboot.demospring.testlist.modelmapper.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Customer {
  Name name;

  public Optional<Name> getName1(){
    return Optional.ofNullable(name);
  }
}
