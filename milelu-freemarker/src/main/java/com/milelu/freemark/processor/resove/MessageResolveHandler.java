package com.milelu.freemark.processor.resove;

import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Channel;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.exception.CustomException;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.processor.channel.BaseChannelService;
import com.milelu.freemark.processor.channel.ChannelThreadLocal;
import com.milelu.freemark.processor.message.MessageModel;
import com.milelu.freemark.processor.message.MessageResolve;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/15 15:16
 */
@Slf4j
public class MessageResolveHandler extends ResolveHandlerAdapt implements MessageResolve {
    private MessageModel messageModel;

    private BaseChannelService channelService;

    private MileluConfig mileluConfig;

    public MessageResolveHandler(MessageModel messageModel, MileluConfig mileluConfig) {
        super(messageModel.getMessage());
        this.messageModel = messageModel;
        this.mileluConfig = mileluConfig;
    }

    @Override
    public void run() {
        String methodName = getMethod();
        Object[] args = getArgs();
        MessageProxy messageProxy = new MessageProxy();
        Object instance = messageProxy.proxyObject(channelService);
        ChannelThreadLocal.set(Channel.SITE_ID, getSiteId());
        ChannelThreadLocal.set(Channel.USER_ID, getUserId());
        Method method;
        try {
            if (CommonUtils.BeNotNull(args)) {
                method = instance.getClass().getMethod(methodName, getClazz(args));
            } else {
                method = instance.getClass().getMethod(methodName);
            }
            if (CommonUtils.BeNotNull(method)) {
                method.invoke(instance, args);
            }
        } catch (Exception e) {
            if (e instanceof NoSuchMethodException) {
                log.error("method:" + methodName + " not fount:" + e.getMessage() + "找不到代理方法，请查看");
            } else if (e instanceof InvocationTargetException) {
                log.error("Target Method:" + methodName + " exception:" + e.getMessage());
            }
            if (!mileluConfig.getKeepOnOfError()) {
                throw new CustomException("当前方法执行异常,线程已停止运行!");
            }
        } finally {
            ChannelThreadLocal.remove();
        }
    }

    private Class<?>[] getClazz(Object[] args) {
        List<Class<?>> classes = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof Map) {
                classes.add(Map.class);
            } else if (arg instanceof List) {
                classes.add(List.class);
            } else {
                classes.add(arg.getClass());
            }
        }
        Class<?>[] cz = new Class<?>[classes.size()];
        return classes.toArray(cz);
    }

    @Override
    public String channel() {
        return messageModel.getChannel();
    }

    @Override
    public void channelService(BaseChannelService channelService) {
        this.channelService = channelService;
    }
}
