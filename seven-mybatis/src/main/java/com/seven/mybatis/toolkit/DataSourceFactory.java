package com.seven.mybatis.toolkit;

import com.alibaba.druid.pool.DruidDataSource;
import com.seven.mybatis.config.DataSourceProperty;
import com.seven.mybatis.config.durid.DruidDataSourceProperties;
import com.seven.mybatis.config.hikari.HikariDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * @author shanhonghao
 * @date 2018-08-22 14:52
 */
@Slf4j
public class DataSourceFactory {

    private DataSourceFactory() {
    }

    private static final String DRUID_DATASOURCE = "com.alibaba.druid.pool.DruidDataSource";
    private static final String HIKARICP_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";

    private static Method createMethod;
    private static Method typeMethod;
    private static Method urlMethod;
    private static Method usernameMethod;
    private static Method passwordMethod;
    private static Method driverClassNameMethod;
    private static Method buildMethod;

    static {
        Class builderClass = null;
        try {
            builderClass = Class.forName("org.springframework.boot.jdbc.DataSourceBuilder");
        } catch (Exception e) {
            try {
                builderClass = Class.forName("org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder");
            } catch (Exception e1) {
                // do nothing
            }
        }
        try {
            createMethod = builderClass.getDeclaredMethod("create");
            typeMethod = builderClass.getDeclaredMethod("type", Class.class);
            urlMethod = builderClass.getDeclaredMethod("url", String.class);
            usernameMethod = builderClass.getDeclaredMethod("username", String.class);
            passwordMethod = builderClass.getDeclaredMethod("password", String.class);
            driverClassNameMethod = builderClass.getDeclaredMethod("driverClassName", String.class);
            buildMethod = builderClass.getDeclaredMethod("build");
        } catch (Exception e) {
            log.error("find DataSourceBuilder failed", e);
        }
    }

    /**
     * 创建数据源
     *
     * @param dataSourceKey      数据源名称
     * @param dataSourceProperty 数据源信息
     * @return 数据源
     */
    public static DataSource createDataSource(String dataSourceKey, DataSourceProperty dataSourceProperty) {
        String type = dataSourceProperty.getType();
        if (type == null) {
            try {
                Class.forName(HIKARICP_DATASOURCE);
                return createHikariDataSource(dataSourceProperty);
            } catch (ClassNotFoundException e) {
                log.warn("will not use connection pool for {}, because default druid dependency not exists", dataSourceKey);
            }
        } else if (DRUID_DATASOURCE.equals(type)) {
            try {
                // 在指定 druid 链接池时, 检查依赖必须要存在
                Class.forName(DRUID_DATASOURCE);
            } catch (ClassNotFoundException e) {
                log.error("want to use druid for {} but failed, because druid dependency not exists", dataSourceKey);
                ReflectionUtils.rethrowRuntimeException(e);
            }
            return createDruidDataSource(dataSourceProperty);
        } else if (HIKARICP_DATASOURCE.equals(type)) {
            try {
                // 在指定 hikari 链接池时, 检查依赖必须要存在
                Class.forName(HIKARICP_DATASOURCE);
            } catch (ClassNotFoundException e) {
                log.error("want to use druid for {} but failed, because hikari dependency not exists", dataSourceKey);
                ReflectionUtils.rethrowRuntimeException(e);
            }
            return createHikariDataSource(dataSourceProperty);
        }
        // 不配置连接池
        return createBasicDataSource(dataSourceProperty);
    }

    @Generated
    private static DataSource createBasicDataSource(DataSourceProperty dataSourceProperty) {
        try {
            Object o1 = createMethod.invoke(null);
            Object o2 = typeMethod.invoke(o1, dataSourceProperty.getType());
            Object o3 = urlMethod.invoke(o2, dataSourceProperty.getUrl());
            Object o4 = usernameMethod.invoke(o3, dataSourceProperty.getUsername());
            Object o5 = passwordMethod.invoke(o4, dataSourceProperty.getPassword());
            Object o6 = driverClassNameMethod.invoke(o5, dataSourceProperty.getDriverClassName());
            return (DataSource) buildMethod.invoke(o6);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("create basic datasource failed", e);
        }
        return null;
    }

    private static DataSource createDruidDataSource(DataSourceProperty dataSourceProperty) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceProperty.getUrl());
        druidDataSource.setUsername(dataSourceProperty.getUsername());
        druidDataSource.setPassword(dataSourceProperty.getPassword());
        druidDataSource.setDriverClassName(dataSourceProperty.getDriverClassName());

        DruidDataSourceProperties druidProperties = dataSourceProperty.getDruid();

        try {
            BeanUtils.copyProperties(druidProperties, druidDataSource);
            druidDataSource.setFilters(druidProperties.getFilters());
            druidDataSource.init();
        } catch (SQLException e) {
            log.error("create druid datasource failed", e);
        }
        return druidDataSource;
    }

    private static DataSource createHikariDataSource(DataSourceProperty dataSourceProperty) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dataSourceProperty.getUrl());
        hikariDataSource.setUsername(dataSourceProperty.getUsername());
        hikariDataSource.setPassword(dataSourceProperty.getPassword());
        hikariDataSource.setDriverClassName(dataSourceProperty.getDriverClassName());

        HikariDataSourceProperties hikariProperties = dataSourceProperty.getHikari();

        try {
            BeanUtils.copyProperties(hikariProperties, hikariDataSource);
        } catch (Exception e) {
            log.error("create hikari datasource failed", e);
        }

        return hikariDataSource;
    }

}
