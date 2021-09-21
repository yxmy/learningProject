package com.yx.mygroup.controller;

import com.yx.mygroup.annotation.ProcessLock;
import com.yx.mygroup.model.User;
import com.yx.mygroup.service.BaseEntityService;
import com.yx.mygroup.model.Person;
import com.yx.mygroup.service.AbstractBaseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author seeyon_yuanxin
 * @date 2020-12-16 17:47
 */
@RestController
public class DemoController {

//    @Autowired
//    private BaseEntityService baseEntityService;

    @Resource(name = "serviceA")
    private AbstractBaseService serviceA;

    @Resource(name = "serviceB")
    private AbstractBaseService serviceB;

    @ProcessLock(lockParam = {"processId","workItemId"}, execMode = "改串行执行")
    @RequestMapping("/index")
    public String index(Model model){
        Person single = new Person("aa", 11);
        List<Person> list = new ArrayList<>();
        list.add(new Person("bb",22));
        list.add(new Person("cc",33));
        list.add(new Person("dd",44));
        model.addAttribute("singlePerson", single);
        model.addAttribute("people", list);
        System.out.println("方法执行结束***********");
        return "index";
    }

    @GetMapping("a")
    public String findA() {
        return serviceA.sayHello();
    }

    @GetMapping("b")
    public String findB() {
        return serviceB.sayHello();
    }

    @GetMapping("/method1")
    public void method1() throws InterruptedException {
        User user = new User();
        BaseEntityService entityService = new BaseEntityService(user);
        entityService.method1();
        Thread.sleep(10000);
        System.out.println("method1 is " + user.getName());
    }

    @GetMapping("/method2")
    public void method2() throws InterruptedException {
        User user = new User();
        BaseEntityService entityService = new BaseEntityService(user);
        entityService.method2();
        System.out.println("method2 is " + user.getName());
    }
}
