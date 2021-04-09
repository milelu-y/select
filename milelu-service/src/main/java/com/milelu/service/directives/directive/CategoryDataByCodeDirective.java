package com.milelu.service.directives.directive;

import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.DirectiveEnum;
import com.milelu.freemark.handler.BaseTemplateDirective;
import com.milelu.freemark.handler.RenderHandler;
import com.milelu.service.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/27 22:56
 */
@Component
public class CategoryDataByCodeDirective extends BaseTemplateDirective {

    @Autowired
    ContentService contentService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code=handler.getString("code");
        String categoryId=handler.getString("categoryId");
        Integer num=handler.getInteger("num");
        String order=handler.getString("order");
        if(CommonUtils.BeNotBlank(code) || CommonUtils.BeNotBlank(categoryId)){
            num = CommonUtils.BeNotNull(num)?num:10;
            List<Map<String,Object>> contents = contentService.listByCode(code,categoryId,num,order);
            handler.put(getName().getCode(),contents).render();
        }
    }

    @Override
    public DirectiveEnum getName() {
        return DirectiveEnum.CATEGORY_DATA_BY_CODE_DIRECTIVE;
    }
}
