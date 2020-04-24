package com.example.database.config;

import com.example.database.interceptor.DataSourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author czx
 * @title: WebMvcConfig
 * @projectName dynamic-data-source
 * @description: TODO
 * @date 2020/4/2416:38
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private DataSourceInterceptor dataSourceInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dataSourceInterceptor)
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
