package com.milelu.service.service.job;

import com.milelu.service.domain.PublishDto;

/**
 * @author MILELU
 * @date 2021/1/26 11:23
 */
public interface JobService {
    /**
     * 定时发布
     * @param publishDto
     */
    void jobPublish(PublishDto publishDto);
}
