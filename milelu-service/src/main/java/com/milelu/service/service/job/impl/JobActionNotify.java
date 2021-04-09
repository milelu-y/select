package com.milelu.service.service.job.impl;

import lombok.Getter;

/**
 * @author MILELU
 * @date 2021/1/26 11:33
 */
public enum JobActionNotify
{
    CONTENT_PUBLISH ("PUBLISH_JOB","GROUP1");

    @Getter
    private String jobName;

    @Getter
    private String group;


    JobActionNotify(String jobName,String group) {
        this.jobName = jobName;
        this.group = group;
    }
}
