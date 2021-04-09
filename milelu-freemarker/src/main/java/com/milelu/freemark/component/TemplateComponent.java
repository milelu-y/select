package com.milelu.freemark.component;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.freemark.handler.FreeMarkerUtils;
import freemarker.template.Configuration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author MILELU
 * 模板指令组件
 * @date 2021/1/15 10:52
 */
@Slf4j
@Data
@Component
public class TemplateComponent {
    @Autowired
    private Configuration configuration;

    /**
     * 创建页面
     *
     * @return
     */
    public AjaxResult createStaticFile(String tempPath, String destPath, Map<String, Object> param, Configuration configuration) {
        AjaxResult apiResult =AjaxResult.success();
        try {
            FreeMarkerUtils.generateFileByFile(tempPath,destPath,configuration,param);
            apiResult.putAll(param);
        } catch (Exception e) {
            log.error(e.getMessage());
            apiResult= AjaxResult.error("页面创建失败",param);
        }
        return apiResult;
    }
}
