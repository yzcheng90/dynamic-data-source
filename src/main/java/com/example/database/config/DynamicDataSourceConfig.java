package com.example.database.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.example.database.constant.DataSourceConstant;
import com.example.database.entity.DefaultDataSourceEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author czx
 * @title: DynamicDataSourceConfig
 * @projectName dynamic-data-source
 * @description: TODO
 * @date 2020/4/2415:19
 */
@Configuration
public class DynamicDataSourceConfig implements TransactionManagementConfigurer {

    @Autowired
    private DefaultDataSourceEntity defaultDataSourceEntity;

    private HashMap<Object,Object> dataSourceMap = new HashMap<>();

    @Bean
    public RoutingDataSourceConfig dataSource() {
        RoutingDataSourceConfig config = new RoutingDataSourceConfig();
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(defaultDataSourceEntity.getUrl());
        hikariDataSource.setDriverClassName(defaultDataSourceEntity.getDriverClassName());
        hikariDataSource.setUsername(defaultDataSourceEntity.getUsername());
        hikariDataSource.setPassword(defaultDataSourceEntity.getPassword());
        config.setDefaultTargetDataSource(hikariDataSource);
        dataSourceMap.put(DataSourceConstant.defaultKey, hikariDataSource);
        config.setTargetDataSources(dataSourceMap);
        return config;
    }

    @PostConstruct
    public void init(){
        dataSource();
        HikariDataSource defaultDataSource = (HikariDataSource) dataSourceMap.get(DataSourceConstant.defaultKey);
        List<Map<String, Object>> dataSourceList = new JdbcTemplate(defaultDataSource).queryForList(DataSourceConstant.defaultQuerySql);
        if(CollUtil.isNotEmpty(dataSourceList)){
            dataSourceList.forEach(ds -> {
                HikariDataSource hikariDataSource = new HikariDataSource();
                hikariDataSource.setJdbcUrl(MapUtil.getStr(ds,DataSourceConstant.DSConnUrl));
                hikariDataSource.setDriverClassName(defaultDataSourceEntity.getDriverClassName());
                hikariDataSource.setUsername(MapUtil.getStr(ds,DataSourceConstant.DSUsername));
                hikariDataSource.setPassword(MapUtil.getStr(ds,DataSourceConstant.DSPassword));
                dataSourceMap.put(MapUtil.getStr(ds,DataSourceConstant.DSDataKey), hikariDataSource);
            });
        }
    }

    public void reset() {
        init();
        RoutingDataSourceConfig dataSource = dataSource();
        dataSource.setTargetDataSources(dataSourceMap);
        dataSource.afterPropertiesSet();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return new ChainedTransactionManager(transactionManager);
    }

    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return platformTransactionManager();
    }
}
