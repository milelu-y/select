package com.milelu.service.service.job.impl;

import com.milelu.common.utils.CommonUtils;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JobExecute  {

    @Autowired
    Scheduler scheduler;


    public void execute(JobActionNotify jobActionNotify, Date date, Class<? extends QuartzJobBean> clz, Map params) {
        synchronized (this) {
            Trigger trigger = jobTrigger(scheduler, jobActionNotify);
            if(CommonUtils.BeNull(trigger)){
                JobUtil.createJobByStartAt(scheduler,date.getTime(), jobActionNotify.getJobName(),jobActionNotify.getGroup(),clz,params);
            }else{
                JobUtil.modifyJobParam(scheduler,trigger,params);
            }
        }
    }

    public void execute(JobActionNotify jobActionNotify, Date date, Class<? extends  QuartzJobBean> clz, Map params,JobExecuteHandler jobExecuteHandler) {
        synchronized (this) {
            Trigger trigger = jobTrigger(scheduler, jobActionNotify);
            if(CommonUtils.BeNull(trigger)){
                JobUtil.createJobByStartAt(scheduler,date.getTime(), jobActionNotify.getJobName(),jobActionNotify.getGroup(),clz,params);
            }else{
                boolean isExecu = jobExecuteHandler.before(scheduler,trigger,params);
                if(isExecu){
                    JobUtil.modifyJobParam(scheduler,trigger,params);
                }
            }
            jobExecuteHandler.after(scheduler,trigger,params);
        }
    }



    protected boolean triggerIsExist(Scheduler scheduler,JobActionNotify jobActionNotify){
        Trigger trigger= jobTrigger(scheduler,jobActionNotify);
        return CommonUtils.BeNotNull(trigger);
    }

    protected Trigger jobTrigger(Scheduler scheduler,JobActionNotify jobActionNotify){
        Trigger trigger=JobUtil.ckJobIsExist(scheduler,jobActionNotify.getJobName(),jobActionNotify.getGroup());
        return trigger;
    }
}
