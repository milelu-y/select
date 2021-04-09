package com.milelu.service.directives.method;

import cn.hutool.core.util.StrUtil;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.MethodEnum;
import com.milelu.freemark.handler.BaseMethod;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MILELU
 * 字符串截取
 */
@Component
public class SubString extends BaseMethod {
    @Override
    public MethodEnum getName() {
        return MethodEnum.SUB;
    }

    @Override
    public Object exec(List list) throws TemplateModelException {
        String str = getString(0, list);
        Integer toIndex = getInteger(1, list);
        if (CommonUtils.BeNull(toIndex)) {
            toIndex = 10;
        }
        if (CommonUtils.BeNotBlank(str)) {
            String suffix = getString(2, list);
            String resStr = StrUtil.sub(str, 0, CommonUtils.BeNotNull(toIndex) ? toIndex : 1);
            if (str.length() > toIndex.intValue()) {
                if (CommonUtils.BeNotBlank(suffix)) {
                    resStr += suffix;
                }
            }
            return resStr;
        }
        return "";
    }
}
