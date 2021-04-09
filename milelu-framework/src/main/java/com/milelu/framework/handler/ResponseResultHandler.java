package com.milelu.framework.handler;

import com.milelu.common.annotation.ResponseResult;
import com.milelu.common.constant.HttpStatus;
import com.milelu.common.core.domain.AjaxResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MILELU
 * @date 2020/12/21 10:38
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    public static final String RESPONSE_RESULT = "RESPONSE-RESULT";

    public static final String RESPONSE_AJAX_FLAG = "RESPONSE_AJAX_FLAG";

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        ResponseResult responseResult = (ResponseResult) request.getAttribute(RESPONSE_RESULT);
        return responseResult != null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        boolean isAjax = (boolean) request.getAttribute(RESPONSE_AJAX_FLAG);
        if (o instanceof AjaxResult) {
            AjaxResult ajax = (AjaxResult) o;
            Integer code = (Integer) ajax.get(AjaxResult.CODE_TAG);
            if (code == HttpStatus.ERROR) {
                return ajax;
            }
        }
        if (isAjax) {
            return AjaxResult.success(o);
        } else {
            return o;
        }
    }
}
