package com.milelu.common.enums;

import lombok.Getter;

/**
 * @author MILELU
 * @date 2021/1/9 16:06
 */
public enum FilterField {
    /**
     * 筛选模型需要使用的默认字段
     */
    MODEL("model", "模型"),
    /**
     * 筛选分类扩展需要使用的默认字段
     */
    CATEGORY("category", "分类扩展"),
    /**
     * 筛选附件扩展需要使用的默认字段
     */
    ATTACH("attach", "附件扩展"),
    /**
     * 筛选页面片段需要使用的默认字段
     */
    FRAGMENT("fragment", "页面片段");

    @Getter
    private final String name;
    @Getter
    private final String code;


    FilterField(String code, String name) {

        this.code = code;
        this.name = name;
    }

    public static FilterField getFilterField(String code) {
        for (FilterField each : FilterField.class.getEnumConstants()) {
            if (code.equals(each.code)) {
                return each;
            }
        }
        return null;
    }
}
