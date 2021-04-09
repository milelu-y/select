package com.milelu.service.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 内容对象 cms_content
 *
 * @author MILELU
 * @date 2021-01-23
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Content extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 站点ID
     */
    @Excel(name = "站点ID")
    private String siteId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 副标题
     */
    @Excel(name = "副标题")
    private String subTitle;

    /**
     * 审核用户
     */
    @Excel(name = "审核用户")
    private String approveUserId;

    /**
     * $column.columnComment
     */
    @Excel(name = "审核用户")
    private String approveUserName;

    /**
     * 分类
     */
    @Excel(name = "分类")
    private String categoryId;

    /**
     * 父分类ID
     */
    @Excel(name = "父分类ID")
    private String pCategoryId;

    /**
     * 模型表ID
     */
    @Excel(name = "模型表ID")
    private String modelId;

    /**
     * 是否转载
     */
    @Excel(name = "是否转载")
    private Boolean copied;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String author;

    /**
     * 编辑
     */
    @Excel(name = "编辑")
    private String editor;

    /**
     * 是否置顶
     */
    @Excel(name = "是否置顶")
    private Boolean istop;

    /**
     * 是否推荐
     */
    @Excel(name = "是否推荐")
    private Boolean isrecomd;

    /**
     * 是否头条
     */
    @Excel(name = "是否头条")
    private Boolean isheadline;

    /**
     * 外链
     */
    @Excel(name = "外链")
    private Boolean onlyUrl;

    /**
     * 拥有附件列表
     */
    @Excel(name = "拥有附件列表")
    private Boolean hasFiles;

    /**
     * 是否有推荐内容
     */
    @Excel(name = "是否有推荐内容")
    private Boolean hasRelated;

    /**
     * 是否包含附件
     */
    @Excel(name = "是否包含附件")
    private Boolean hasTags;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String url;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String description;

    /**
     * 标签
     */
    @Excel(name = "标签")
    private String tagIds;

    /**
     * 置顶标签
     */
    @Excel(name = "置顶标签")
    private String topTag;

    /**
     * 封面
     */
    @Excel(name = "封面")
    private String cover;

    /**
     * 评论数
     */
    @Excel(name = "评论数")
    private Long comments;

    /**
     * 点击数
     */
    @Excel(name = "点击数")
    private Long clicks;

    /**
     * 日期生成规则
     */
    @Excel(name = "日期生成规则")
    private String pathRule;

    /**
     * 点赞数
     */
    @Excel(name = "点赞数")
    private Long giveLikes;

    /**
     * 发布日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;

    /**
     * 发表用户ID
     */
    @Excel(name = "发表用户ID")
    private String publishUserId;

    /**
     * 定时发布日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "定时发布日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date jobDate;

    /**
     * 审核日期(c)
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核日期(c)", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approveDate;

    /**
     * 顺序
     */
    @Excel(name = "顺序")
    private Long sort;

    /**
     * 状态：0、草稿 1、已发布 2、删除
     */
    @Excel(name = "状态：0、草稿 1、已发布 2、删除")
    private String status;

    /**
     * 创建用户id
     */
    @Excel(name = "创建用户id")
    private String createId;

    /**
     * 修改人id
     */
    @Excel(name = "修改人id")
    private String modifiedId;

    /**
     * 创建人名称
     */
    @Excel(name = "创建人名称")
    private String createName;

    /**
     * 修改人名称
     */
    @Excel(name = "修改人名称")
    private String modifiedName;

    /***
     * 以下扩展
     */
    private List<String> categoryIds;

    private String coverStr;

    private String categoryName;

    private String modelName;

    private String text;

    /**
     * 内容来源
     */
    private String origin;

    /**
     * 来源地址
     */
    private String originUrl;

    List<String> contentIds;
    private Map<String,Object> params;
}
