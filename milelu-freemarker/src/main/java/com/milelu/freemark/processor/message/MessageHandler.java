package com.milelu.freemark.processor.message;

import com.milelu.freemark.processor.channel.BaseChannelService;
import com.milelu.freemark.processor.resove.MessageResolveHandler;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author MILELU
 * @date 2021/1/15 15:07
 */
public abstract class MessageHandler implements MessageActuator,MessageListener{

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    protected Map<String, BaseChannelService> channelServiceMap = new HashMap<>();

    @Override
    public void submit(MessageResolve messageResolve){
        messageResolve.channelService(channelServiceMap.get(messageResolve.channel()));
        cachedThreadPool.submit(messageResolve);
    }

}
