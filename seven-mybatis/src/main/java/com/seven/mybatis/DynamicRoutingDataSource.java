package com.seven.mybatis;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/25 16:07
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Getter
    private Map<String, DataSource> dataSourceMap;

    @Setter
    private DynamicDataSourceProvider provider;

    /**
     * 默认数据源名称，默认master，可为组数据源名，可为单数据源名
     */
    @Setter
    private String primary;

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
