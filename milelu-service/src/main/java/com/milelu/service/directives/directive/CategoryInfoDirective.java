package com.milelu.service.directives.directive;

import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.DirectiveEnum;
import com.milelu.freemark.handler.BaseTemplateDirective;
import com.milelu.freemark.handler.RenderHandler;
import com.milelu.service.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/27 17:16
 */
@Component
public class CategoryInfoDirective extends BaseTemplateDirective {

    @Autowired
    CategoryService categoryService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code=handler.getString("code");
        String id=handler.getString("id");
        if(CommonUtils.BeNotBlank(code)){
            Map<String,Object> category = categoryService.info(code,id);
            handler.putAll(category).render();
        }
    }

    @Override
    public DirectiveEnum getName() {
        return DirectiveEnum.CMS_CATEGORY_DIRECTIVE;
    }
}
