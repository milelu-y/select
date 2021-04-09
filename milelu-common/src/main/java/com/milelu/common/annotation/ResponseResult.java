package com.milelu.common.annotation;

import java.lang.annotation.*;

/**
 * @author MILELU
 * @date 2020/12/18 14:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface ResponseResult {


    /**
     * 是否需要AjaxResult
     */

    boolean isAjaxResult() default false;


}
