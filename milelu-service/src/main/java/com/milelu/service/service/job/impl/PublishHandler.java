package com.milelu.service.service.job.impl;

import com.milelu.common.utils.CommonUtils;
import com.milelu.service.domain.PublishDto;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PublishHandler extends JobExecuteHandler {
    @Override
    public boolean before(Scheduler scheduler, Trigger trigger, Map<String, Object> param) {
        PublishDto publishDto = (PublishDto)trigger.getJobDataMap().get("bean");
        PublishDto newBean = (PublishDto)param.get("bean");
        List<String> ids = publishDto.getIds();
        if(CommonUtils.BeEmpty(ids)){
            ids = new ArrayList<>();
        }
        if(CommonUtils.BeNotEmpty(newBean.getIds())){
            for (String id : newBean.getIds()) {
                if (!ids.contains(id)) {
                    ids.add(id);
                }
            }
        }
        trigger.getTriggerBuilder().startAt(newBean.getDate());
        newBean.setIds(ids);
        return true;
    }

    @Override
    public void after(Scheduler scheduler, Trigger trigger, Map<String, Object> param) {

    }
}
