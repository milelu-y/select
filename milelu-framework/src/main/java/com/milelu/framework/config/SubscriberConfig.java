package com.milelu.framework.config;

import cn.hutool.core.util.EnumUtil;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.message.RedisMessage;
import org.aspectj.weaver.Checker;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;

@Configuration
@AutoConfigureAfter({RedisMessage.class})
public class SubscriberConfig {

    /**
     * 消息监听适配器，注入接受消息方法，输入方法名字 反射方法
     *
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter getMessageListenerAdapter(RedisMessage receiver) {
        return new MessageListenerAdapter(receiver,"onMessage"); //当没有继承MessageListener时需要写方法名字
    }

    /**
     * 创建消息监听容器
     *
     * @param redisConnectionFactory
     * @param messageListenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
                                            MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        List<Object> channels= EnumUtil.getFieldValues(ChannelEnum.class,"code");
        if(CommonUtils.BeNotEmpty(channels)){
            for(Object channel:channels){
                // new PatternTopic("这里是监听的通道的名字") 通道要和发布者发布消息的通道一致
                container.addMessageListener(messageListenerAdapter, new PatternTopic(channel.toString()));
            }
        }
        return container;
    }

}
