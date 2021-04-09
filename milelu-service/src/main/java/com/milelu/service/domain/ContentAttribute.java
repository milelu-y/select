package com.milelu.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 内容扩展对象 cms_content_attribute
 *
 * @author MILELU
 * @date 2021-01-25
 */
@Data
@Accessors(chain = true)
public class ContentAttribute extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private String id;

    /**
     * 主键
     */
    private String contentId;

    /**
     * 内容来源
     */
    @Excel(name = "内容来源")
    private String origin;

    /**
     * 来源地址
     */
    @Excel(name = "来源地址")
    private String originUrl;

    /**
     * 数据JSON
     */
    @Excel(name = "数据JSON")
    private String data;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String text;

    /**
     * 字数
     */
    @Excel(name = "字数")
    private Long totalWordCount;
}
