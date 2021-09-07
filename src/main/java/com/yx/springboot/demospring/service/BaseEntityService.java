package com.yx.springboot.demospring.service;

import com.yx.springboot.demospring.model.BaseEntity;
import com.yx.springboot.demospring.dao.BaseEntityDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yuanxin
 * @date 2021/7/13
 */
@Transactional
@Service
@Slf4j
public class BaseEntityService {

    @Autowired
    private BaseEntityDao baseEntityDao;

    public void method1() throws InterruptedException {
        BaseEntity entity = baseEntityDao.findById(123L).get();
        entity.setName("123");
        baseEntityDao.save(entity);
        Thread.sleep(2000L);
    }


    public void method2() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            final BaseEntity baseEntity = baseEntityDao.findById(123L).get();
            log.info("baseEntity的name为：{}", baseEntity.getName());
            Thread.sleep(2000L);
        }
    }

}
