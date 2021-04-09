package com.milelu.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import com.milelu.common.utils.model.BaseField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 内容附件对象 cms_content_attach
 *
 * @author MILELU
 * @date 2021-01-25
 */
@Data
@Accessors(chain = true)
public class ContentAttach extends BaseField {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String contentId;

    /**
     * 关联资源表
     */
    @Excel(name = "关联资源表")
    private String fileUid;

    /**
     * $column.columnComment
     */
    @Excel(name = "关联资源表")
    private String url;

    /**
     * $column.columnComment
     */
    @Excel(name = "关联资源表")
    private String name;

    /**
     * 下载数
     */
    @Excel(name = "下载数")
    private Long downs;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * json 字段
     */
    @Excel(name = "json 字段")
    private String data;

    /** 创建者 */
    private String createBy;

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

}
