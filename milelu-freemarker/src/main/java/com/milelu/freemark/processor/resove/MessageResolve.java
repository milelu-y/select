package com.milelu.freemark.processor.resove;

import com.milelu.freemark.processor.channel.BaseChannelService;

/**
 * @author MILELU
 * @date 2021/1/15 15:16
 */
public interface MessageResolve extends Runnable{
    String channel();

    void channelService(BaseChannelService channelService);
}
