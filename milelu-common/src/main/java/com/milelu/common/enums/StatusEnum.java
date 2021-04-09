package com.milelu.common.enums;

import lombok.Getter;

/**
 * @author MILELU
 * @date 2021/1/25 16:39
 */
public enum StatusEnum {
    DRAFT("0", "草稿"),
    DELETE("2", "已删除"),
    PUBLISH("1", "已发布");

    @Getter
    private String code;
    @Getter
    private String name;

    StatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static StatusEnum getStatusEnum(String code) {
        for (StatusEnum each : StatusEnum.class.getEnumConstants()) {
            if (code.equals(each.code)) {
                return each;
            }
        }
        return null;
    }
}
