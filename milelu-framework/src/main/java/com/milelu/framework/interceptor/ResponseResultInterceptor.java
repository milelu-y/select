package com.milelu.framework.interceptor;

import com.milelu.common.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author MILELU
 * @date 2020/12/18 14:34
 */
@Component
public class ResponseResultInterceptor extends HandlerInterceptorAdapter {

    public static final String RESPONSE_RESULT = "RESPONSE-RESULT";

    public static final String RESPONSE_AJAX_FLAG = "RESPONSE_AJAX_FLAG";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(ResponseResult.class)) {
                ResponseResult annotation = method.getAnnotation(ResponseResult.class);
                boolean flag=false;
                if (annotation!=null){
                    flag=annotation.isAjaxResult();
                }
                request.setAttribute(RESPONSE_AJAX_FLAG,flag);
                request.setAttribute(RESPONSE_RESULT, annotation);
            }
        }
        return true;
    }
}
