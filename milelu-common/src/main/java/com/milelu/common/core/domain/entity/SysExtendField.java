package com.milelu.common.core.domain.entity;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 扩展字段对象 sys_extend_field
 *
 * @author MILELU
 * @date 2020-12-23
 */
@Data
public class SysExtendField extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 扩展ID
     */
    private Long extendId;

    /**
     * 编码
     */
    private String code;

    /**
     * 是否必填
     */
    @Excel(name = "是否必填")
    private Boolean required;

    /**
     * 是否可搜索
     */
    @Excel(name = "是否可搜索")
    private Integer searchable;

    /**
     * 最大长度
     */
    @Excel(name = "最大长度")
    private Long maxlength;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 解释
     */
    @Excel(name = "解释")
    private String description;

    /**
     * 表单类型
     */
    @Excel(name = "表单类型")
    private String inputType;

    /**
     * 默认值
     */
    @Excel(name = "默认值")
    private String defaultValue;


    /**
     * 顺序
     */
    @Excel(name = "顺序")
    private Long sort;


    /**
     * 别名
     */
    private String fieldAliase;

}
