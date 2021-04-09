package com.milelu.service.directives.directive;

import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.DirectiveEnum;
import com.milelu.freemark.handler.BaseTemplateDirective;
import com.milelu.freemark.handler.RenderHandler;
import com.milelu.service.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/28 15:34
 */
@Component
public class ContentNextPreviousDirective extends BaseTemplateDirective {
    @Autowired
    ContentService contentService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String id=handler.getString("id");
        String categoryId=handler.getString("categoryId");
        if(CommonUtils.BeNotBlank(id)){
            Map<String,Object> content = contentService.nextPrevious(id,categoryId);
            handler.putAll(content).render();
        }
    }

    @Override
    public DirectiveEnum getName() {
        return DirectiveEnum.CMS_CATEGORY_CONTENG_NEXTPREVIOUS;
    }
}
