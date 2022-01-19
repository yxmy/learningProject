package com.yx.mygroup.service.impl;

import com.yx.mygroup.annotation.ProcessLock;
import com.yx.mygroup.service.AbstractBaseService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;

/**
 * @author seeyon_yuanxin
 * @date 2020-12-16 17:43
 */
@Order
@Service("serviceA")
@ProcessLock(lockParam = "a")
public class ServiceA extends AbstractBaseService {
    @Override
    public String sayHello() {
        return "hello，A";
    }
}
