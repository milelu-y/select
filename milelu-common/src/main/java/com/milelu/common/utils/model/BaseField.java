package com.milelu.common.utils.model;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author MILELU
 * @date 2021/1/10 19:03
 */
@Data
@Accessors(chain = true)
public class BaseField extends BaseEntity {

    /**
     * 默认自带
     */
    @Excel(name = "默认自带")
    private String defaultFieldList;

    /**
     * 扩展字段
     */
    @Excel(name = "扩展字段")
    private String extendFieldList;

    /**
     * 所有选择字段（包含选择默认+扩展）
     */
    @Excel(name = "所有选择字段", readConverterExp = "包=含选择默认+扩展")
    private String allFieldList;

    /**
     * 必填字段
     */
    @Excel(name = "必填字段")
    private String requiredFieldList;

    /**
     * 字段对应中文名称
     */
    @Excel(name = "字段对应中文名称")
    private String fieldTextMap;
}
