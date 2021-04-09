package com.milelu.freemark.emums;

import lombok.Getter;

public enum DirectiveEnum {

    /**
     * 获取栏目数
     */
    NAVBAR("navbar", "bars", "获取栏目数"),

    /**
     * 获取站点信息
     */
    SITE("site", "site", "获取站点信息"),

    /**
     * 获取页面片段数据
     */
    FRAGMENT_DATA("fdata", "datas", "获取页面片段数据"),

    /**
     * 根据栏目编码获取栏目内容
     */
    CATEGORY_DATA_BY_CODE_DIRECTIVE("cdata", "contents", "根据栏目编码获取栏目内容"),

    /**
     * 获取栏目详情
     */
    CMS_CATEGORY_DIRECTIVE("category", "category", "获取栏目详情"),

    /**
     * 获取内容详情
     */
    CMS_CONTENT_INFO_DIRECTIVE("content", "category", "获取内容详情"),

    /**
     * 获取单页栏目内容
     */
    CMS_CATEGORY_SINGLE_DATA_DIRECTIVE("singleData", "content", "获取单页栏目内容"),

    /**
     * 获取上/下 一页
     */
    CMS_CATEGORY_CONTENG_NEXTPREVIOUS("nextPrevious", "content", "获取上/下 一页"),

    /**
     * 获取面包屑导航
     */
    BREADCRUMB_DIRECTIVE("breadCrumb", "categorys", "获取面包屑导航"),

    /**
     * 获取指定栏目的最新内容
     */
    CMS_TOPNEW_DIRECTIVE("topNew", "contents", "获取指定栏目的最新内容"),

    /**
     * 获取置顶标签的文章列表
     */
    CMS_TOPTGS_DIRECTIVE("topTags", "tags", "获取置顶标签的文章列表"),

    /**
     * 获取标签
     */
    CMS_TGS_DIRECTIVE("tags", "tags", "获取标签");


    @Getter
    private String value;

    @Getter
    private String code;

    @Getter
    private String name;

    @Getter
    private String sourceTarget;


    DirectiveEnum(String value, String code, String name) {
        this.name = name;
        this.value = value;
        this.code = code;
    }

    DirectiveEnum(String value, String code, String name, String sourceTarget) {
        this.name = name;
        this.value = value;
        this.code = code;
        this.sourceTarget = sourceTarget;
    }
}
