package com.milelu.freemark.processor.message;

import com.milelu.common.constant.Channel;
import com.milelu.common.core.base.BaseContextKit;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.SecurityUtils;
import com.milelu.freemark.emums.ChannelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.Unsafe;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/15 14:54
 * 消息发送者
 */
@Component
public class MessageSend {
    @Autowired
    RedisTemplate redisTemplate;

    public void sendMessage(ChannelEnum channelEnum, String method, Object... args){
        if(CommonUtils.BeNotBlank(channelEnum.getCode())&&CommonUtils.BeNotBlank(method)){
            redisTemplate.convertAndSend(channelEnum.getCode(),msg(method,args));
        }
    }

    private Map<String,Object> msg(String method, Object[] args){
        Map<String,Object> msgMap = new HashMap<>();
        msgMap.put(Channel.METHOD,method);
        if(CommonUtils.BeNotNull(args)){
            msgMap.put(Channel.ARGS,args);
        }
        String siteId = "123";
        if(CommonUtils.BeNotBlank(siteId)){
            msgMap.put(Channel.SITE_ID,siteId);
        }
        String userId = BaseContextKit.getUserId();
        if(CommonUtils.BeNotBlank(userId)){
            msgMap.put(Channel.USER_ID,userId);
        }
        msgMap.put(Channel.DOMAIN,domain());
        BaseContextKit.remove();
        return msgMap;
    }

    private String domain(){
        RequestAttributes requestAttributes= RequestContextHolder.getRequestAttributes();
        if(CommonUtils.BeNotNull(requestAttributes)){
            HttpServletRequest request =((ServletRequestAttributes) requestAttributes).getRequest();
            return request.getServerName();
        }
        return null;
    }
}
