package com.milelu.service.directives.directive;

import com.milelu.freemark.emums.DirectiveEnum;
import com.milelu.freemark.handler.BaseTemplateDirective;
import com.milelu.freemark.handler.RenderHandler;
import com.milelu.service.service.file.FragmentAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/27 14:25
 */
@Component
public class FragmentDataDirective extends BaseTemplateDirective {
    @Autowired
    FragmentAttributeService attrService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code=handler.getString("code");
        List<Map> fragmentDatas=attrService.listDataByCode(code);
        handler.put(getName().getCode(),fragmentDatas).render();
    }

    @Override
    public DirectiveEnum getName() {
        return DirectiveEnum.FRAGMENT_DATA;
    }
}
