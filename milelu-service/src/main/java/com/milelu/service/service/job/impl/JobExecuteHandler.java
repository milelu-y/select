package com.milelu.service.service.job.impl;

import org.quartz.Scheduler;
import org.quartz.Trigger;

import java.util.Map;

public abstract class JobExecuteHandler {


    public abstract boolean before(Scheduler scheduler, Trigger trigger, Map<String, Object> param);

    public abstract void after(Scheduler scheduler,Trigger trigger, Map<String, Object> param);
}
