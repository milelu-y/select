package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分类模型对象 cms_category_model
 *
 * @author MILELU
 * @date 2021-01-19
 */
@Data
@Accessors(chain = true)
public class CategoryModel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String siteId;

    /**
     * 分类模型扩展名称
     */
    @Excel(name = "分类模型扩展名称")
    private String categoryModelName;

    /**
     * 扩展字段(json 保存)
     */
    @Excel(name = "扩展字段(json 保存)")
    private String extendFieldList;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;


}
