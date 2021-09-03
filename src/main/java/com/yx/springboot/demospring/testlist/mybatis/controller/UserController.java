package com.yx.springboot.demospring.testlist.mybatis.controller;

import com.yx.springboot.demospring.testlist.mybatis.mapper.UserMapper;
import com.yx.springboot.demospring.testlist.mybatis.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    private UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @GetMapping("list")
    public List<UserEntity> list() {
        return userMapper.listSimple();
    }

    @GetMapping("list/{username}")
    public List<UserEntity> listByUsername(@PathVariable("username") String username) {
        return userMapper.listByUsername(username);
    }

    @GetMapping("get/{username}/{password}")
    public UserEntity get(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userMapper.get(username, password);
    }

    @GetMapping("get/bad/{username}/{password}")
    public UserEntity getBadUser(@PathVariable("username") String username, @PathVariable("password") String password) {
        return userMapper.getUser(username, password);
    }
}
