package com.milelu.freemark.processor.resove;

import com.milelu.freemark.processor.channel.BaseChannelService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class MessageProxy implements InvocationHandler {
    /**
     * 真实对象
     */
    private  Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(target instanceof BaseChannelService){
            BaseChannelService channelService =(BaseChannelService) target;
            return method.invoke(channelService.clone(false),args);
        }
        return method.invoke(target,args);
    }

    public Object proxyObject(Object target){
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),new Class[]{BaseChannelService.class},this);
    }
}
