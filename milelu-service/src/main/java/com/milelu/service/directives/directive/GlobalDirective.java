package com.milelu.service.directives.directive;

import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Channel;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.handler.DirectiveInterceptor;
import com.milelu.freemark.processor.channel.ChannelThreadLocal;
import com.milelu.service.service.site.SiteService;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/15 13:35
 */
@Component
public class GlobalDirective implements DirectiveInterceptor {

    @Autowired
    Configuration configuration;

    @Autowired
    SiteService siteService;

    @Autowired
    MileluConfig mileluConfig;

    @Override
    public Configuration injectionGlobalVariable(Map<String, Object> params) {
        return null;
    }

    @Override
    public Configuration injectionShareVariable(Map<String, Object> params) {
        Map<String,Object> variables = new HashMap<>();
        variables.putAll(domainCode());
        if(CommonUtils.BeNotNull(params) && !params.isEmpty()){
            variables.putAll(params);
        }
        return injectionShare(variables);
    }

    private Configuration injectionShare(Map<String, Object> variables) {
        Configuration cloneConf = (Configuration)configuration.clone();
        DefaultObjectWrapper objectWrapper = (DefaultObjectWrapper)configuration.getObjectWrapper();
        try {
            if(CommonUtils.BeNotNull(variables) && !variables.isEmpty()){
                for(Map.Entry<String, Object> entry : variables.entrySet()){
                    cloneConf.setSharedVariable(entry.getKey(),objectWrapper.wrap(entry.getValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneConf;
    }

    private Map<String,Object> domainCode(){
        //TODO:后期动态获取
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("dt",mileluConfig.getDomain());
        variables.put("domain",mileluConfig.getDomain());
        return variables;
    }
}
