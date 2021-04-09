package com.milelu.common.utils.model;

import com.milelu.common.enums.InputType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author MILELU
 * @date 2021/1/9 16:46
 */

@Data
@Accessors(chain = true)
public class DynamicModel implements Comparable<DynamicModel>{


    private static final long serialVersionUID = 1L;
    /**
     *     必须勾选
      */
    private Boolean check;

    /**
     * 字段code
     */
    private String fieldCode;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段别名称
     */
    private String fieldAliase;

    /**
     * input 类型
     */
    private String inputType;

    /**
     * 是否必填
     */
    private Boolean isRequired;

    /**
     *是否可搜索
     */
    private Boolean isSearch;

    /**
     * 是否扩展字段
     */
    private Boolean isExtend;

    /**
     * 默认值
     */
    private Object defaultValue;

    /**
     * 描述
     */
    private String description ;

    /**
     * 最大长度
     */
    private Integer maxlength;

    /**
     * 排序
     */
    private Integer sort ;

    /**
     * 过滤
     */
    private List<String> filterField;


    @Override
    public int compareTo(DynamicModel o) {
        return InputType.getSort(this.getInputType()) - InputType.getSort(o.getInputType()) ;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DynamicModel) {
            DynamicModel model = (DynamicModel) obj;
            return this.getFieldCode().equals(model.getFieldCode());
        }
        return super.equals(obj);
    }
}
