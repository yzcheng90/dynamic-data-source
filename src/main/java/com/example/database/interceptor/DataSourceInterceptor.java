package com.example.database.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.database.constant.DataSourceConstant;
import com.example.database.holder.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author czx
 * @title: DataSourceInterceptor
 * @projectName dynamic-data-source
 * @description: TODO
 * @date 2020/4/2416:00
 */
@Slf4j
@Configuration
public class DataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String dataSourceHeader = request.getHeader(DataSourceConstant.dataSourceHeader);
        if(StrUtil.isNotBlank(dataSourceHeader)){
            log.info("设置数据源：{}",dataSourceHeader);
            DynamicDataSourceContextHolder.setDataSource(dataSourceHeader);
        }else {
            log.info("使用默认数据源！");
            DynamicDataSourceContextHolder.setDataSource(DataSourceConstant.defaultKey);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        DynamicDataSourceContextHolder.clearDataSource();
    }
}
