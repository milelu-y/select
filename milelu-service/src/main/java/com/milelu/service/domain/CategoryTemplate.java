package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分类-模板配置对象 cms_category_template
 *
 * @author MILELU
 * @date 2021-01-20
 */
@Data
@Accessors(chain = true)
public class CategoryTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    private String categoryId;

    /**
     * 站点ID
     */
    @Excel(name = "站点ID")
    private String siteId;

    /**
     * 模板ID
     */
    @Excel(name = "模板ID")
    private String templateId;

    /**
     * 模板列表页路径
     */
    @Excel(name = "模板列表页路径")
    private String templatePath;
}
