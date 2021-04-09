package com.milelu.service.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.annotation.Excel;
import com.milelu.common.core.domain.BaseEntity;
import com.milelu.common.utils.model.BaseField;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 页面片段文件模型对象 cms_fragment_model
 *
 * @author MILELU
 * @date 2021-01-07
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FragmentModel extends BaseField {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 别名
     */
    @Excel(name = "别名")
    private String alias;

    @NotBlank(message = "路径不能为空")
    private String path;

    @NotBlank(message = "相对路径不能为空")
    private String relativePath;

    /**
     * 片段编码
     */
    @Excel(name = "片段编码")
    private String code;

    /**
     * 模板id
     */
    @Excel(name = "模板id")
    private String templateId;

    /**
     * 展示条数
     */
    @Excel(name = "展示条数")
    private Long size;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String fileName;

}
