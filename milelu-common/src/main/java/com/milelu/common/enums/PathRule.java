package com.milelu.common.enums;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import com.milelu.common.utils.model.KeyValueModel;
import lombok.Getter;

import java.util.*;

public enum PathRule {

    /**
     * 年月日/随机ID.HTML
     */
    YYYYMMDD_UUID("YYYYMMDD/UUID", "年月日/随机ID.HTML"),

    /**
     * 年月日/文章ID.HTML
     */
    YYYYMMDD_ID("YYYYMMDD/ID", "年月日/文章ID.HTML"),

    /**
     * 年-月-日/文章ID.HTML
     */
    YYYY_MM_DD("YYYY-MM-DD/ID", "年-月-日/文章ID.HTML"),

    /**
     * 栏目编码/年月日/文章ID.HTML
     */
    CODE_YYYYMMDD_ID("CODE/YYYYMMDD/ID", "栏目编码/年月日/文章ID.HTML"),

    /**
     * 栏目编码/年月日/随机ID.HTML
     */
    CODE_YYYYMMDD_UUID("CODE/YYYYMMDD/UUID", "栏目编码/年月日/随机ID.HTML"),

    /**
     * 栏目编码/随机数.HTML
     */
    CODE_UUID("CODE/UUID", "栏目编码/随机数.HTML"),

    /**
     * 栏目编码/文章ID.HTML
     */
    CODE_ID("CODE/ID", "栏目编码/文章ID.HTML");

    @Getter
    private String rule;

    @Getter
    private String name;


    PathRule(String rule, String name) {
        this.rule = rule;
        this.name = name;
    }


    /**
     * @param code :栏目编码 id:文章ID
     * @return
     */
    public String format(String code, String id) {
        String rule = this.getRule();
        if (this.equals(YYYYMMDD_UUID)) {
            rule = rule.replace("YYYYMMDD", DateUtil.format(new Date(), "yyyyMMdd")).replace("UUID", UUID.randomUUID().toString());
        } else if (this.equals(YYYYMMDD_ID)) {
            rule = rule.replace("YYYYMMDD", DateUtil.format(new Date(), "yyyyMMdd")).replace("ID", id);
        } else if (this.equals(YYYY_MM_DD)) {
            rule = rule.replace("YYYY-MM-DD", DateUtil.format(new Date(), "yyyy-MM-dd")).replace("ID", id);
        } else if (this.equals(CODE_YYYYMMDD_ID)) {
            rule = rule.replace("CODE", code).replace("YYYYMMDD", DateUtil.format(new Date(), "yyyyMMdd")).replace("ID", id);
        } else if (this.equals(CODE_YYYYMMDD_UUID)) {
            rule = rule.replace("CODE", code).replace("YYYYMMDD", DateUtil.format(new Date(), "yyyyMMdd")).replace("UUID", UUID.randomUUID().toString());
        } else if (this.equals(CODE_UUID)) {
            rule = rule.replace("CODE", code).replace("UUID", UUID.randomUUID().toString());
        } else if (this.equals(CODE_ID)) {
            rule = rule.replace("CODE", code).replace("ID", id);
        } else {
            rule = rule.replace("YYYYMMDD", DateUtil.format(new Date(), "yyyyMMdd")).replace("UUID", UUID.randomUUID().toString());
        }
        return "/" + rule;
    }

    public static PathRule getPathRule(String rule) {
        for (PathRule each : PathRule.class.getEnumConstants()) {
            if (rule.equals(each.rule)) {
                return each;
            }
        }
        return YYYYMMDD_UUID;
    }

    public static List<KeyValueModel> keyValues() {
        Map<String, Object> obs = EnumUtil.getNameFieldMap(PathRule.class, "name");
        List<KeyValueModel> keyValueModels = new ArrayList<>();
        if (!obs.isEmpty()) {
            for (String key : obs.keySet()) {
                PathRule pathRule = Enum.valueOf(PathRule.class, key);
                keyValueModels.add(new KeyValueModel(pathRule.getRule(), obs.get(key).toString()));
            }
        }
        return keyValueModels;
    }
}
