package com.milelu.common.enums;

import lombok.Getter;

/**
 * @author MILELU
 * @date 2021/1/9 16:50
 */
public enum InputType {
    /**
     *
     */
    INPUT_TEXT("普通文本框","text",1),
    /**
     *
     */
    INPUT_NUMBER("普通数字框","number",2),
    INPUT_DATE("普通日期框","date",3),
    /**
     *
     */
    INPUT_SWITCH("普通开关框","switch",5),
    INPUT_RADIO("普通单选框","radio",7),
    /**
     *
     */
    INPUT_CHECKBOX("普通多选框","checkbox",9),
    /**
     *
     */
    INPUT_PICTURE("普通图片框","picture",11),
    /**
     *
     */
    INPUT_TAG("普通标签框","tags",13),
    /**
     *
     */
    INPUT_FILE("普通文件框","file",15),
    /**
     *
     */
    INPUT_TEXTAREA("普通区域框","textarea",17),
    /**
     *
     */
    INPUT_EDITOR("普通编辑器","editor",19),
    /**
     *
     */
    INPUT_ATTACH("普通附件框","attach",19);

    @Getter
    private String name;

    @Getter
    private String code;

    @Getter
    private Integer sort;


    InputType(String name, String code, Integer sort) {
        this.name =name;
        this.code = code;
        this.sort =sort;
    }

    public static  Integer getSort(String code){
        for (InputType each : InputType.class.getEnumConstants()) {
            if (code.equals(each.code)) {
                return each.getSort();
            }
        }
        return 20;
    }


}
