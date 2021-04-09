package com.milelu.service.directives.directive;

import com.milelu.freemark.emums.DirectiveEnum;
import com.milelu.freemark.handler.BaseTemplateDirective;
import com.milelu.freemark.handler.RenderHandler;
import com.milelu.service.domain.CategoryNavbar;
import com.milelu.service.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author MILELU
 * @date 2021/1/27 15:47
 */
@Component
public class NavbarDirective extends BaseTemplateDirective {

    @Autowired
    CategoryService categoryService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        Boolean showCount = handler.getBoolean("showCount");
        Boolean showHideMenu = handler.getBoolean("showHideMenu");
        List<CategoryNavbar> trees = categoryService.navbar(code,showCount,showHideMenu);
        handler.put(getName().getCode(),trees).render();
    }

    @Override
    public DirectiveEnum getName() {
        return DirectiveEnum.NAVBAR;
    }
}
