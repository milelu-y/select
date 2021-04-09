package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 选版模板对象 select_template
 *
 * @author MILELU
 * @date 2021-04-09
 */
@Data
public class SelectTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 分类id */
    @Excel(name = "分类id")
    private Long categoryId;

    /** 资源id */
    @Excel(name = "资源id")
    private Integer resourceId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 路由地址 */
    @Excel(name = "路由地址")
    private String path;

    /** 是否缓存（0缓存 1不缓存） */
    @Excel(name = "是否缓存", readConverterExp = "0=缓存,1=不缓存")
    private Integer isCache;

    /** 类型（0 h5 1文档） */
    @Excel(name = "类型", readConverterExp = "0=,h=5,1=文档")
    private Integer type;

    /** 封面 */
    @Excel(name = "封面")
    private String picture;

    /** 价格 */
    @Excel(name = "价格")
    private Long price;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String reserve1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String reserve2;
}
