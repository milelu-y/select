package com.milelu.service.domain;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 分类对象 cms_category
 *
 * @author MILELU
 * @date 2021-01-18
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 父栏目名称
     */
    private String parentName;

    /**
     * 父分类ID
     */
    @Excel(name = "父分类ID")
    private String parentId;

    /**
     * 站点ID
     */
    @Excel(name = "站点ID")
    private String siteId;

    /**
     * 编码
     */
    @Excel(name = "编码")
    private String code;

    /**
     * 分类图片
     */
    @Excel(name = "分类图片")
    private String cover;

    /**
     * 生成路径规则
     */
    @Excel(name = "生成路径规则")
    private String pathRule;

    /**
     * 分类首页路径
     */
    @Excel(name = "分类首页路径")
    private String path;

    /**
     * 模板路径
     */
    private String templatePath;

    /**
     * 是否只外链 只是外链栏目直接访问
     */
    @Excel(name = "是否只外链 只是外链栏目直接访问")
    private Boolean onlyUrl;

    /**
     * 外链跳转地址
     */
    @Excel(name = "外链跳转地址")
    private String remoteUrl;

    /**
     * 每页数据条数
     */
    @Excel(name = "每页数据条数")
    private Long pageSize;

    /**
     * 允许投稿
     */
    @Excel(name = "允许投稿")
    private Boolean allowContribute;

    /**
     * 顺序
     */
    @Excel(name = "顺序")
    private Long sort;

    /**
     * 是否在首页隐藏
     */
    @Excel(name = "是否在首页隐藏")
    private Boolean hidden;

    /**
     * 扩展模型ID
     */
    @Excel(name = "扩展模型ID")
    private String categoryModelId;

    /**
     * 是否是单页
     */
    @Excel(name = "是否是单页")
    private Boolean singlePage;

    /**
     * 指定生成前多少页
     */
    @Excel(name = "指定生成前多少页")
    private Integer topPages;

    /**
     * 是否包含字内容
     */
    @Excel(name = "是否包含字内容")
    private Boolean containChild;

    /**
     * -----attr-------------
     */
    private String title;

    private String keywords;

    private String description;

    private String data;

    private Map<String, Object> params;
}
