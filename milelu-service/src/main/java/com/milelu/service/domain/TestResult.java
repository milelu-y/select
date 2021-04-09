package com.milelu.service.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author MILELU
 * @date 2021/3/15 10:52
 */
@Data
@Accessors(chain = true)
public class TestResult {
    private String categoryId;

    private Integer minute;

    private String orderNum;
}
