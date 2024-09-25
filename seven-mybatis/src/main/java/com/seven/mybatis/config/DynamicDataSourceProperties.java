package com.seven.mybatis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shanhonghao
 * @date 2018-08-22 11:17
 */
@Getter
@Setter
@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX, ignoreUnknownFields = false)
@SuppressWarnings("squid:S1068")
public class DynamicDataSourceProperties {

    public static final String PREFIX = "longfor.data.database";

    /**
     * 必须设置默认的库,默认master
     */
    private String primary = "master";

    /**
     * 每一个数据源
     */
    private Map<String, DataSourceProperty> connection = new LinkedHashMap<>();

}
