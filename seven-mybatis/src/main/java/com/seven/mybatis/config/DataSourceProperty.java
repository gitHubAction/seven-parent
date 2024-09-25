package com.seven.mybatis.config;

import com.seven.mybatis.config.durid.DruidDataSourceProperties;
import com.seven.mybatis.config.hikari.HikariDataSourceProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author shanhonghao
 * @date 2018-08-22 14:41
 */
@Getter
@Setter
@SuppressWarnings("squid:S1068")
public class DataSourceProperty {
    /**
     * JDBC type,如果不设置自动查找 Druid > HikariCp
     */
    private String type;

    /**
     * JDBC driver
     */
    private String driverClassName;

    /**
     * JDBC url 地址
     */
    private String url;

    /**
     * JDBC 用户名
     */
    private String username;

    /**
     * JDBC 密码
     */
    private String password;

    /**
     * Druid 参数配置
     */
    @NestedConfigurationProperty
    private DruidDataSourceProperties druid = new DruidDataSourceProperties();

    /**
     * Hikari 参数配置
     */
    @NestedConfigurationProperty
    private HikariDataSourceProperties hikari = new HikariDataSourceProperties();
}
