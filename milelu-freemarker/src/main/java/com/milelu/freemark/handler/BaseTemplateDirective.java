package com.milelu.freemark.handler;
import freemarker.core.Environment;
import freemarker.template.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

@Data
public abstract class BaseTemplateDirective implements TemplateDirectiveModel, Directive{

    @Autowired
    DirectiveInterceptor globalDirective;

    @Override
    public void execute(Environment environment, Map parameters, TemplateModel[] loopVars,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        try {
            execute(new TemplateDirectiveHandler(parameters, loopVars, environment, templateDirectiveBody,globalDirective));
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new TemplateException(e, environment);
        }
    }
}
