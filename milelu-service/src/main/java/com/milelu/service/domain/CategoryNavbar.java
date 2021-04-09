package com.milelu.service.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/27 15:49
 */
@Data
@Accessors(chain = true)
public class CategoryNavbar {

    private String id;

    /**
     * 名称
     */

    private String name;


    /**
     * 父分类ID
     */

    private String parentId;


    /**
     * 站点ID
     */

    private String siteId;


    /**
     * 编码
     */

    private String code;


    /**
     * 生成路径规则
     */

    private String pathRule;


    /**
     * 分类首页路径
     */

    private String path;


    /**
     * 是否只外链 只是外链栏目直接访问
     */

    private Boolean onlyUrl;


    /**
     * 外链跳转地址
     */

    private String remoteUrl;


    /**
     * 每页数据条数
     */

    private Integer pageSize;

    private Integer topPages;


    /**
     * 允许投稿
     */

    private Boolean allowContribute;


    /**
     * 顺序
     */

    private Integer sort;


    /**
     * 是否在首页隐藏
     */

    private Boolean hidden;


    /**
     * 扩展ID
     */
    private String categoryModelId;


    /**
     * 是否是单页
     */

    private Boolean singlePage;


    private String title;


    /**
     * 关键词用于 SEO 关键字优化
     */
    private String keywords;


    /**
     * 描述用于 SEO 关键字优化
     */
    private String description;


    /**
     * 数据JSON
     */
    private String data;

    private List<CategoryNavbar> children = new ArrayList<>();

    private Boolean hasChildren = false;

    /**
     * 扩展属性
     */
    private Map ext;


    private Integer count;
}
