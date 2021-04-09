package com.milelu.freemark.handler;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;

/**
 * @author MILELU
 * @date 2021/1/15 11:01
 */
@Slf4j
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {
    @Override
    public void handleTemplateException(TemplateException e, Environment environment, Writer out) throws TemplateException {
        log.error(e.getMessage());
        try {
            out.write("[ERROR]");
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        throw e;
    }
}
