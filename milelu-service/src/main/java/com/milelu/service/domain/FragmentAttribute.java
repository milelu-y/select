package com.milelu.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 页面片段数据对象 cms_fragment_attribute
 *
 * @author MILELU
 * @date 2021-01-11
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FragmentAttribute extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String siteId;

    /** 封面图 */
    @Excel(name = "封面图")
    private String cover;

    /** 片段路径 */
    @Excel(name = "片段路径")
    private String path;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** $column.columnComment */
    @Excel(name = "标题")
    private String description;

    /** 超链接 */
    @Excel(name = "超链接")
    private String url;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 扩展数据data */
    @Excel(name = "扩展数据data")
    private String data;

    /** 模板id */
    @Excel(name = "模板id")
    private String templateId;

    /** $column.columnComment */
    @Excel(name = "模板id")
    private String fragmentModelId;

    /** 0:未激活 1：已激活 */
    @Excel(name = "0:未激活 1：已激活")
    private Integer activate;
}
