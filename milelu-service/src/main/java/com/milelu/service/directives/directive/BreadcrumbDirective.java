package com.milelu.service.directives.directive;

import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.DirectiveEnum;
import com.milelu.freemark.handler.BaseTemplateDirective;
import com.milelu.freemark.handler.RenderHandler;
import com.milelu.service.domain.Category;
import com.milelu.service.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author MILELU
 * @date 2021/1/27 20:38
 */
@Component
public class BreadcrumbDirective extends BaseTemplateDirective {
    @Autowired
    CategoryService categoryService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String id=handler.getString("categoryId"); // 栏目ID
        String postfix=handler.getString("postfix"); // 面包 分隔符
        Boolean containit=handler.getBoolean("containit"); // 包含本栏目
        if(CommonUtils.BeNotBlank(id)){
            if(CommonUtils.BeNull(postfix)){
                postfix = "";
            }
            if(CommonUtils.BeNull(containit)){
                containit = true;
            }
            List<Category> categorys = categoryService.breadCrumbs(id,postfix,containit);
            if(CommonUtils.BeNotEmpty(categorys)){
                Collections.reverse(categorys);
            }
            handler.put(getName().getCode(),categorys).render();
        }
    }

    @Override
    public DirectiveEnum getName() {
        return DirectiveEnum.BREADCRUMB_DIRECTIVE;
    }
}
