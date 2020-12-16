package com.yx.springboot.demospring.testlist.model;


import com.yx.springboot.demospring.testlist.enums.FilmPublishVersion;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 影片资料录入<br/>
 * 1、该类作为Film管理类使用。<br/>
 * 2、影片介绍(FilmIntroductionInfo)对应一个现实空间我们理解的一个影片，同一个影片根据不同的发行版本
 * FilmPublishVersion（2D、3D）对应出不同影片编码Film关联类。<br/>
 * 例如：《钢铁侠》是一个电影，钢铁侠主演以及概要等信息是影片介绍（FilmIntroductionInfo），
 * 从管理的角度来讲钢铁侠2D和钢铁侠3D是两个不同的管理实例(Film)。<br/>
 */
@Getter
@Setter
@Entity
@Table(indexes = {@Index(columnList = "code", name = "IDX_FILM_CODE"),
        @Index(columnList = "publishName", name = "IDX_FILM_NAME"),
        @Index(columnList = "premiereDate", name = "IDX_FILM_PREMIERE_DATE") })
public class Film{

    private static final long serialVersionUID = 3776498253149022877L;

    /**
     * 影片编码
     */
    @NotBlank
    @Length(max = 12)
    @Column(nullable = false, length = 12, unique = true)
    private String code;

    /**
     * 影片名称
     */
    @NotBlank
    @Length(max = 128)
    @Column(nullable = false, length = 128)
    private String name;

    /**
     * 影片发布名称
     */
    @NotBlank
    @Length(max = 128)
    @Column(nullable = false, length = 128)
    private String publishName;

    /**
     * 影片基础信息
     */
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private FilmBasicInfo basicInfo = new FilmBasicInfo();

    /**
     * 影片简介信息
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private FilmIntroductionInfo introductionInfo;

    /**
     * 可用状态
     */
    private boolean actived = true;

    /**
     * 发行版本
     */
    @Enumerated(EnumType.STRING)
    private FilmPublishVersion publishVersion;

    /**
     * 是否加入票房<br/>
     * 备注：预留字段，暂时不使用
     */
    private boolean boxofficeFlag = true;

    /**
     * 首映日期
     */
    @Temporal(TemporalType.DATE)
    private Date premiereDate;

    /**
     * 公映日期
     */
    @Temporal(TemporalType.DATE)
    private Date showDate;
}
