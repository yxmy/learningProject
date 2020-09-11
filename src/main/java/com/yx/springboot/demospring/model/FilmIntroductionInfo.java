package com.yx.springboot.demospring.model;

import com.yx.springboot.demospring.enums.FilmCategory;
import com.yx.springboot.demospring.enums.FilmType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 影片摘要信息
 */
@Getter
@Setter
@Entity
public class FilmIntroductionInfo{

    private static final long serialVersionUID = 7245356092133086824L;

    /**
     * 影片名称
     */
    @NotBlank
    @Length(max = 1000)
    @Column(nullable = false, length = 1000)
    private String name;

    /**
     * 别名
     */
    @Length(max = 1000)
    @Column(length = 1000)
    private String alias;

    /**
     * 片种
     */
    @Enumerated(EnumType.STRING)
    private FilmCategory category;

    /**
     * 影片类别
     */
    @Enumerated(EnumType.STRING)
    private FilmType type;

    /**
     * 导演
     */
    @Length(max = 1000)
    @Column(length = 1000)
    private String director;

    /**
     * 制片人
     */
    @Length(max = 100)
    @Column(length = 100)
    private String producer;

    /**
     * 演员表
     */
    @Length(max = 1000)
    @Column(length = 1000)
    private String cast;

    /**
     * 简介
     */
    @Length(max = 3000)
    @Column(length = 3000)
    private String introduction;

    /**
     * 编剧
     */
    @Length(max = 600)
    @Column(length = 600)
    private String writer;

    /**
     * 摄影
     */
    @Length(max = 200)
    @Column(length = 200)
    private String shoot;

    /**
     * 片长（分钟）
     */
    private Integer filmLength;

}
