package com.milelu.freemark.component;

import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Constants;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.handler.BaseMethod;
import com.milelu.freemark.handler.BaseTemplateDirective;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MILELU
 * 注册所有指令
 * @date 2021/1/15 10:56
 */
@Component
public class DirectiveComponent {
    @Autowired
    MileluConfig mileluConfig;
    @Autowired
    public void init(Configuration configuration, List<BaseTemplateDirective> templateDirectives, List<BaseMethod> baseMethodHandlers) throws TemplateModelException {
        Map<String, Object> freemarkerVariables = new HashMap<>();
        if(CommonUtils.BeNotEmpty(templateDirectives)){
            for (BaseTemplateDirective directive:templateDirectives){
                freemarkerVariables.put(directive.getName().getValue(),directive);
            }
        }
        if(CommonUtils.BeNotEmpty(baseMethodHandlers)){
            for (BaseMethod baseMethod:baseMethodHandlers){
                freemarkerVariables.put(baseMethod.getName().getValue(),baseMethod);
            }
        }
        configuration.setAllSharedVariables(new SimpleHash(freemarkerVariables,configuration.getObjectWrapper()));
        configuration.setSharedVariable(Constants.DOMAIN, mileluConfig.getDomain());
        configuration.setSharedVariable(Constants.SERVER, mileluConfig.getServerDomain());
    }
}
