package com.example.database.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author czx
 * @title: DefaultDataSourceEntity
 * @projectName dynamic-data-source
 * @description: TODO 默认配置
 * @date 2020/4/2415:28
 */
@Data
@Component
@ConfigurationProperties("spring.datasource")
public class DefaultDataSourceEntity {

    private String driverClassName;
    private String username;
    private String password;
    private String url;
}
