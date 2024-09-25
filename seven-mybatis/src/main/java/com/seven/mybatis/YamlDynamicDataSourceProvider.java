package com.seven.mybatis;

import com.seven.mybatis.config.DataSourceProperty;
import com.seven.mybatis.config.DynamicDataSourceProperties;
import com.seven.mybatis.toolkit.DataSourceFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * yaml 数据源提供者
 *
 * @author shanhonghao
 * @date 2018-08-22 14:50
 */
public class YamlDynamicDataSourceProvider implements DynamicDataSourceProvider {

    private DynamicDataSourceProperties properties;

    public YamlDynamicDataSourceProvider(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, DataSource> loadDataSources() {
        Map<String, DataSourceProperty> dataSourcePropertiesMap = properties.getConnection();
        Map<String, DataSource> dataSourceMap = new HashMap<>(dataSourcePropertiesMap.size());
        for (Map.Entry<String, DataSourceProperty> item : dataSourcePropertiesMap.entrySet()) {
            dataSourceMap.put(item.getKey(), DataSourceFactory.createDataSource(item.getKey(), item.getValue()));
        }
        return dataSourceMap;
    }
}
