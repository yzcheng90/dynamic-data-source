package com.example.database.config;

import com.example.database.holder.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author czx
 * @title: RoutingDataSourceConfig
 * @projectName dynamic-data-source
 * @description: TODO 数据库路由配置
 * @date 2020/4/2415:26
 */
public class RoutingDataSourceConfig extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSource();
    }
}
