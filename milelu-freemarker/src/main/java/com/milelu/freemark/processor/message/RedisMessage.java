package com.milelu.freemark.processor.message;

import com.milelu.common.config.MileluConfig;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.processor.channel.BaseChannelService;
import com.milelu.freemark.processor.resove.MessageResolveHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MILELU
 * @date 2021/1/15 14:54
 * 消息接收者
 */
@Component
public class RedisMessage extends MessageHandler {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MileluConfig mileluConfig;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisSerializer<Object> serializer = redisTemplate.getValueSerializer();
        Object msg = serializer.deserialize(message.getBody());
        MessageModel messageModel = new MessageModel(msg, new String(message.getChannel()));
        submit(new MessageResolveHandler(messageModel, mileluConfig));
    }

    @Autowired
    public void channelService(List<BaseChannelService> baseChannelServices) {
        if (CommonUtils.BeNotEmpty(baseChannelServices)) {
            for (BaseChannelService channelService : baseChannelServices) {
                channelServiceMap.put(channelService.getName().getCode(), channelService);
            }
        }
    }
}
