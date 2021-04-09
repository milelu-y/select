package com.milelu.web.controller.job;

import com.milelu.service.domain.PublishDto;
import com.milelu.service.service.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MILELU
 * @date 2021/1/26 11:22
 */
@Validated
@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    JobService jobService;


    /**
     * 定时发布
     * @param publishDto
     */
    @PutMapping("/jobPublish")
    public void jobPublish(@Validated @RequestBody PublishDto publishDto){
        jobService.jobPublish(publishDto.setStatus("1"));
    }
}
