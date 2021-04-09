package com.milelu.freemark.component;

import com.milelu.common.constant.Channel;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知组件
 * @author MILELU
 */
@Slf4j
@Component
public class NotifyComponent extends AbstractNotify{

    @Autowired
    SimpMessagingTemplate SMT;

    @Override
    public void notifyMsg(String principal, Object data) {
        if(CommonUtils.BeNotNull(data)){
            if(data instanceof AjaxResult){
                AjaxResult msgData = (AjaxResult) data;
                if(msgData.containsKey(Channel.DEST_PATH)){
                    Map<String,Object> map = new HashMap<>(8);
                    map.put(Channel.DEST_PATH,msgData.get(Channel.DEST_PATH));
                    map.put(Channel.PERCENT,CommonUtils.BeNotNull(msgData.get(Channel.PERCENT))?msgData.get(Channel.PERCENT):100);
                    map.put(Channel.CHANNER_NAME,msgData.get(Channel.CHANNER_NAME));
                    map.put("msg",msgData.get("msg"));
                    SMT.convertAndSendToUser(principal,"/queue/msg",map);
                }
            }
        }
    }
}
