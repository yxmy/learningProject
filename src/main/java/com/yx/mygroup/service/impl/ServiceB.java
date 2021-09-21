package com.yx.mygroup.service.impl;

import com.yx.mygroup.service.AbstractBaseService;
import org.springframework.stereotype.Service;

/**
 * @author seeyon_yuanxin
 * @date 2020-12-16 17:43
 */
@Service("serviceB")
public class ServiceB extends AbstractBaseService {
    @Override
    public String sayHello() {
        return "hello，B";
    }
}
