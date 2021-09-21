package com.yx.mygroup.dao;

import com.yx.mygroup.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yuanxin
 * @date 2021/7/13
 */
public interface BaseEntityDao extends JpaRepository<BaseEntity, Long> {
}
