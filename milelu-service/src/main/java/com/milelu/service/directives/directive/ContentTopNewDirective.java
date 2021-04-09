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
 * 获取指定/不指定 栏目 是否置顶  是否推荐 是否头条
 * @date 2021/1/28 15:41
 */
@Component
public class ContentTopNewDirective extends BaseTemplateDirective {
    @Autowired
    ContentService contentService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        // 分类id
        String categoryId=handler.getString("categoryId");
        // 分类编码
        String code=handler.getString("code");
        // 条件
        String where=handler.getString("where");
        if(CommonUtils.BeNotBlank(where)){
            Map<String, List> maps = contentService.getTopNews(categoryId,code,where);
            handler.putAll(maps).render();
        }
    }

    @Override
    public DirectiveEnum getName() {
        return DirectiveEnum.CMS_TOPNEW_DIRECTIVE;
    }
}
