package com.milelu.freemark.emums;

import lombok.Getter;

public enum  StatusEnum {
    DRAFT("0", "草稿状态"),
    PUHLISH("1", "发布状态"),
    DELETE("2", "删除状态");
    @Getter
    private String name;

    @Getter
    private String code;


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
