package com.milelu.common.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.milelu.common.core.domain.entity.SysExtendField;
import com.milelu.common.utils.model.DynamicModel;
import lombok.Data;

import java.util.List;

/**
 * @author MILELU
 * @date 2020/12/23 11:01
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 别名
     */
    private String alias;

    /**
     * 路径
     */
    private String path;
    /**
     * 发布路径
     */
    private String publishPath;



    /**
     * 是否动态
     */
    private boolean useDynamic;

    /**
     * 是否需要登录
     */
    private boolean needLogin;
    /**
     * 是否需要身体
     */
    private boolean needBody;

    /**
     * 接收参数
     */
    private String acceptParameters;

    /**
     * 缓存时间
     */
    private Integer cacheTime;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 扩展字段
     */
    private List<DynamicModel> extendList;

}
