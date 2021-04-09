package com.milelu.service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.milelu.common.utils.model.BaseField;;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author LG
 * @since 2019-10-23
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldDto extends BaseField {

    private static final long serialVersionUID = 1L;

    /**
     * 模型名称
     */
    @NotBlank(message = "模型名称不能为空")
    private String name;

    /**
     * 静态化模板地址
     */
    @NotBlank(message = "模板地址不能为空")
    private String templatePath;

    /**
     * 模板ID
     */
    @NotBlank(message = "模板地址不能为空")
    private String templateId;

    private String md5Key;


   // @NotBlank(message = "模型模板地址不能为空",groups = {ValidGroup2.class})
    private String modelTemplateId;

    /**
     * 是否有用图片
     */
    private Boolean hasImages;

    /**
     * 是否拥有文件
     */
    private Boolean hasFiles;

    private Boolean isUrl;

}
