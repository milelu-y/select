package com.milelu.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.security.DenyAll;

/**
 * 资源对象 sys_resource
 *
 * @author MILELU
 * @date 2021-01-13
 */
@Data
@Accessors(chain = true)
public class SysResource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 文件唯一标识 */
    @Excel(name = "文件唯一标识")
    private String fileUid;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 标题 */
    @Excel(name = "标题")
    private String fileMd5;

    /** 文件大小kb */
    @Excel(name = "文件大小kb")
    private Long fileSize;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** url */
    @Excel(name = "url")
    private String url;

    /** 相对地址 */
    @Excel(name = "相对地址")
    private String filePath;

    /** 全地址 */
    @Excel(name = "全地址")
    private String fileFullPath;

    /** 组 */
    @Excel(name = "组")
    private String groupName;
}
