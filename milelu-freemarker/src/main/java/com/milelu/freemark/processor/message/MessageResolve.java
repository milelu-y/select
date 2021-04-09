package com.milelu.freemark.processor.message;

import com.milelu.freemark.processor.channel.BaseChannelService;

public interface MessageResolve extends Runnable{

     String channel();

     void channelService(BaseChannelService channelService);
}
