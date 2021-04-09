package com.milelu.service.directives.method;

import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.MethodEnum;
import com.milelu.freemark.handler.BaseMethod;
import com.milelu.service.service.file.FragmentModelService;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MILELU
 * @date 2021/1/15 13:58
 */
@Component
public class FragmentImport extends BaseMethod {

    @Autowired
    FragmentModelService fragmentModelService;

    @Override
    public MethodEnum getName() {
        return MethodEnum.IMPORT;
    }

    @Override
    public Object exec(List list) throws TemplateModelException {
        String code = getString(0, list);
        if (CommonUtils.BeNotBlank(code)) {
            String fragmentPath = fragmentModelService.getFragmentPathByCode(code);
            if (CommonUtils.BeNotBlank(fragmentPath)) {
                return fragmentPath;
            }
        }
        return null;
    }
}
