package com.milelu.freemark.config;

import com.milelu.common.config.MileluConfig;
import com.milelu.freemark.handler.FreemarkerExceptionHandler;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author MILELU
 * @date 2020/12/15 22:35
 */
@Configuration
public class FreeMarkConfig {

    @Bean
    public freemarker.template.Configuration configuration(MileluConfig mileluConfig) throws IOException, TemplateException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_28);
        configuration.setDirectoryForTemplateLoading(initFile(mileluConfig.getTemplate()));
        configuration.setObjectWrapper(new DefaultObjectWrapper(freemarker.template.Configuration.VERSION_2_3_28));
        configuration.setTemplateExceptionHandler(templateExceptionHandler());
        configuration.setDefaultEncoding("UTF-8");
        configuration.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
        configuration.setSetting("number_format", "0.##");
        return configuration;
    }

    @Bean
    TemplateExceptionHandler templateExceptionHandler() {
        return new FreemarkerExceptionHandler();
    }

    private File initFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
