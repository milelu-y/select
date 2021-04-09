package com.milelu.service.domain;

import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;


/**
 * 站点对象 sys_site
 * 
 * @author MILELU
 * @date 2020-12-14
 */
@Data
public class Site extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 父站点ID */
    @Excel(name = "父站点ID")
    private Integer parentId;

    /** 站点名 */
    @Excel(name = "站点名")
    private String name;

    /** 站点标题 */
    @Excel(name = "站点标题")
    private String siteTitle;

    /** 关键字 */
    @Excel(name = "关键字")
    private String siteKeywords;

    /** 站点描述 */
    @Excel(name = "站点描述")
    private String siteDesc;

    /** 站点logo */
    @Excel(name = "站点logo")
    private String siteLogo;

    /** 启用静态化 */
    @Excel(name = "启用静态化")
    private Integer useStatic;

    /** 站点地址 */
    @Excel(name = "站点地址")
    private String sitePath;

    /** 站点备案信息 */
    @Excel(name = "站点备案信息")
    private String siteicp;

    /** 站点版权 */
    @Excel(name = "站点版权")
    private String copyright;

    /** 启用服务器端包含 */
    @Excel(name = "启用服务器端包含")
    private Integer useSsi;

    /** 动态站点地址 */
    @Excel(name = "动态站点地址")
    private String dynamicPath;

    /** 禁用 */
    @Excel(name = "禁用")
    private Integer disabled;

    /** 第三方评论代码 */
    @Excel(name = "第三方评论代码")
    private String commentCode;

    /** 第三方分享代码 */
    @Excel(name = "第三方分享代码")
    private String shareCode;

    /** 第三方统计代码 */
    @Excel(name = "第三方统计代码")
    private String statisticalCode;

}
