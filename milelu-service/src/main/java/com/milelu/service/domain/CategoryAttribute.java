package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分类扩展对象 cms_category_attribute
 *
 * @author MILELU
 * @date 2021-01-20
 */
@Data
@Accessors(chain = true)
public class CategoryAttribute extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private String categoryId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 关键词
     */
    @Excel(name = "关键词")
    private String keywords;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 数据JSON
     */
    @Excel(name = "数据JSON")
    private String data;
}
