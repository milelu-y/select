package com.milelu.service.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import com.milelu.common.utils.model.BaseField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 模型对象 cms_model
 *
 * @author MILELU
 * @date 2021-01-21
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Model extends BaseField {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 站点模型
     */
    @Excel(name = "站点模型")
    private String siteId;

    /**
     * 模型名称
     */
    @Excel(name = "模型名称")
    private String name;

    /**
     * 是否有用图片
     */
    @Excel(name = "是否有用图片")
    private Boolean hasImages;

    /**
     * 是否拥有文件
     */
    @Excel(name = "是否拥有文件")
    private Boolean hasFiles;

    /**
     * 是否网址
     */
    @Excel(name = "是否网址")
    private Boolean isUrl;

    /** 创建者 */
    private String createBy;

    private Boolean check = false;

    /**模板地址*/
    private String templatePath;

    private String md5Key;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户id */
    @Excel(name = "创建用户id")
    private String createId;

    /** 修改人id */
    @Excel(name = "修改人id")
    private String modifiedId;

    private String modelTemplateId;
}
