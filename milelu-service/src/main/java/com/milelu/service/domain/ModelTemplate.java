package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 模型模板针对不同的站点不同的模型下有不同的 模板页面对象 cms_model_template
 *
 * @author MILELU
 * @date 2021-01-21
 */
@Data
@Accessors(chain = true)
public class ModelTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 模型ID
     */
    @Excel(name = "模型ID")
    private String modelId;

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
     * 内容静态化模板地址
     */
    @Excel(name = "内容静态化模板地址")
    private String templatePath;

}
