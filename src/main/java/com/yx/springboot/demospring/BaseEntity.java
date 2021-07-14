package com.yx.springboot.demospring;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yuanxin
 * @date 2021/7/13
 */
@Getter
@Setter
@Entity
@Table(name = "base_entity")
public class BaseEntity {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT COMMENT 'id'")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(128) COMMENT '名称'")
    private String name;
}
