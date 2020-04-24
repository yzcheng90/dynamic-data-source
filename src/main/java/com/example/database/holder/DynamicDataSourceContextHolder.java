package com.example.database.holder;

import lombok.experimental.UtilityClass;


/**
 * @author czx
 * @title: DataSourceInterceptor
 * @projectName dynamic-data-source
 * @description: TODO
 * @date 2020/4/2416:02
 */
@UtilityClass
public class DynamicDataSourceContextHolder {
	private final ThreadLocal<String> currentThreadDataResource = new ThreadLocal<>();

	public void setDataSource(String dataSourceType) {
		currentThreadDataResource.set(dataSourceType);
	}

	public String getDataSource() {
		return currentThreadDataResource.get();
	}

	public void clearDataSource() {
		currentThreadDataResource.remove();
	}
}