package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分类选择模型时关系 该模型值 tk_model  不是 分类模型对象 cms_category_model_relation
 *
 * @author MILELU
 * @date 2021-01-22
 */
@Data
@Accessors(chain = true)
public class CategoryModelRelation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String siteId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String modelId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String categoryId;

    /**
     * 模板ID
     */
    @Excel(name = "模板ID")
    private String templateId;

    /**
     * $column.columnComment
     */
    @Excel(name = "模板地址")
    private String templatePath;

}
