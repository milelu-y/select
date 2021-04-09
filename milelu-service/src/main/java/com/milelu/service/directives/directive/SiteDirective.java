package com.milelu.service.directives.directive;

import com.milelu.freemark.emums.DirectiveEnum;
import com.milelu.freemark.handler.BaseTemplateDirective;
import com.milelu.freemark.handler.RenderHandler;
import com.milelu.service.service.site.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/15 11:24
 */
@Component
public class SiteDirective extends BaseTemplateDirective {

    @Autowired
    private SiteService siteService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        Map<String,Object> siteInfo = siteService.getSiteInfo();
        handler.putAll(siteInfo).render();
    }

    @Override
    public DirectiveEnum getName() {
        return DirectiveEnum.SITE;
    }
}
