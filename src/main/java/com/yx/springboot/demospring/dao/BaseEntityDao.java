package com.yx.springboot.demospring.dao;

import com.yx.springboot.demospring.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yuanxin
 * @date 2021/7/13
 */
public interface BaseEntityDao extends JpaRepository<BaseEntity, Long> {
}
