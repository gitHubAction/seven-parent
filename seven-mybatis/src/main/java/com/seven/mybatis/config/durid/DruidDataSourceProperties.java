package com.seven.mybatis.config.durid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Properties;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/25 16:19
 */
@Getter
@Setter
public class DruidDataSourceProperties {

    private Integer initialSize = 3;
    private Integer maxActive = 8;
    private Integer minIdle = 0;
    private Long maxWait = -1L;
    private Long timeBetweenEvictionRunsMillis = 60 * 1000L;
    private Long minEvictableIdleTimeMillis = 1000L * 60L * 30L;
    private Long maxEvictableIdleTimeMillis = 1000L * 60L * 60L * 7;
    private String validationQuery = "select 1";
    private Integer validationQueryTimeout = -1;
    private Boolean testOnBorrow = false;
    private Boolean testOnReturn = false;
    private Boolean testWhileIdle = true;
    private Boolean poolPreparedStatements = false;
    private Integer maxOpenPreparedStatements = -1;
    private Boolean sharePreparedStatements = false;
    private Properties connectionProperties;
    private String filters = "stat,wall";
    private String webStatFilterExclusions = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*";
    private Boolean removeAbandoned = false;
    private Long removeAbandonedTimeoutMillis = 300000L;

    /**
     * wall配置
     */
    @NestedConfigurationProperty
    private DruidWallConfigProperties wall;

    private Integer connectTimeout  = 10000;

    private Integer socketTimeout  = 10000;
}
