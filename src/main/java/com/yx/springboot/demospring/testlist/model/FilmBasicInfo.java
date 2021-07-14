package com.yx.springboot.demospring.testlist.model;

import com.yx.springboot.demospring.testlist.enums.FilmCopyType;
import com.yx.springboot.demospring.testlist.enums.FilmPublishType;
import com.yx.springboot.demospring.testlist.enums.FilmSplitBillType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * 影片资料基本信息<br/>
 */
@Getter
@Setter
@Entity
public class FilmBasicInfo{

    private static final long serialVersionUID = 4140367217635446997L;

    @Id
    @Column(name = "id", columnDefinition = "BIGINT COMMENT 'id'")
    private Long id;

    /**
     * 发行类型
     */
    @Enumerated(EnumType.STRING)
    private FilmPublishType publishType;

    /**
     * 发行日期
     */
    @Temporal(TemporalType.DATE)
    private Date publishDate;

    /**
     * 发行范围
     */
    @Length(max = 64)
    @Column(length = 64)
    private String productRange;

    /**
     * 国籍
     */
    @Length(max = 12)
    @Column(length = 12)
    private String countryId;

    /**
     * 公映年代
     */
    private Integer showYear;

    /**
     * 拷贝类型
     */
    @Enumerated(EnumType.STRING)
    private FilmCopyType copyType;

    /**
     * 是否公映
     */
    private boolean showFlag = false;

    /**
     * 声音制式
     */
    @Length(max = 64)
    @Column(length = 64)
    private String audioFormat;

    /**
     * 幅别
     */
    @Length(max = 64)
    @Column(length = 64)
    private String filmWidth;

    /**
     * 分账类型
     */
    @Enumerated(EnumType.STRING)
    private FilmSplitBillType splitBillType;

    /**
     * 公映许可证号
     */
    @Length(max = 128)
    @Column(length = 128)
    private String showLicence;

    /**
     * 公映许可证日期
     */
    @Temporal(TemporalType.DATE)
    private Date showLicenceDate;
    
    /**
     * 开始日期(无具体业务)
     */
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * 结束日期(无具体业务)
     */
    @Temporal(TemporalType.DATE)
    private Date endDate;

}
