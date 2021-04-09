package com.milelu.service.service.job.impl;

import com.milelu.common.utils.CommonUtils;
import com.milelu.service.domain.PublishDto;
import com.milelu.service.service.content.ContentService;
import com.milelu.service.service.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/26 11:24
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobExecute jobExecute;

    @Autowired
    ContentService contentService;

    /**
     * 定时发布
     * @param publishDto
     */
    @Override
    public void jobPublish(PublishDto publishDto) {
        if(CommonUtils.BeNotEmpty(publishDto.getIds())){
            List<String> ids = publishDto.getIds();
            Map<String, Object> jobBean = new HashMap<>(4);
            jobBean.put("bean",publishDto);
            jobExecute.execute(JobActionNotify.CONTENT_PUBLISH,publishDto.getDate(), ContentPublishJob.class,jobBean,new PublishHandler());
            contentService.jobPublish(ids,publishDto.getDate());
        }
    }
}
