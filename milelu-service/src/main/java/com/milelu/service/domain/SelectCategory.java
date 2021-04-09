package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 选版-分类对象 select_category
 *
 * @author MILELU
 * @date 2021-04-09
 */
@Data
public class SelectCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String name;

    /** 预留字段 */
    @Excel(name = "预留字段")
    private String reserve1;

    /** 预留字段 */
    @Excel(name = "预留字段")
    private String reserve2;
}
