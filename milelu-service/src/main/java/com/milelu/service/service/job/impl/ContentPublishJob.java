package com.milelu.service.service.job.impl;

import com.milelu.common.utils.SpringContextHolder;
import com.milelu.service.domain.PublishDto;
import com.milelu.service.service.content.ContentService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/26 11:37
 */
@Component
public class ContentPublishJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ContentService contentService = SpringContextHolder.getBean("contentServiceImpl");
        Map<String, Object> wrappedMap =
                jobExecutionContext.getTrigger().getJobDataMap().getWrappedMap();
        PublishDto publishDto = (PublishDto) wrappedMap.get("bean");
        contentService.publish(publishDto,true);
    }

}
