package com.milelu.framework.web;

import com.milelu.common.core.domain.entity.SysUser;
import com.milelu.common.core.domain.model.LoginUser;
import com.milelu.common.security.CustomSocketPrincipal;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.SpringContextHolder;
import com.milelu.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Checker;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

/**
 * @author MILELU
 * @date 2020/9/10 12:15
 */
@Slf4j
public class HandleShakeInterceptors implements HandshakeInterceptor {

    private TokenService tokenService= SpringContextHolder.getBean(TokenService.class);;
    /**
     * 在握手之前执行该方法, 继续握手返回true, 中断握手返回false.
     * 通过attributes参数设置WebSocketSession的属性
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        //保存客户端标识
        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
        String token = req.getServletRequest().getParameter("token");
        if(CommonUtils.BeBlank(token)){
            log.error("token can't be null websocket权限拒绝");
            return false;
        }
        Principal principal=ckToken(token,req);
        if(CommonUtils.BeNull(principal)){
            log.error("token 无效 websocket权限拒绝");
            return false;
        }
        attributes.put("socketUser", principal);
        return true;
    }

    private Principal ckToken(String token,ServletServerHttpRequest request) {
        LoginUser loginUser = tokenService.getSinToken(token);
        if (CommonUtils.BeNotNull(loginUser)){
            SysUser user = loginUser.getUser();
            if (CommonUtils.BeNotNull(user)){
                return new CustomSocketPrincipal(user.getUserId().toString(),user.getUserName());
            }

        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println(exception);
    }
}

