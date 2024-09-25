package com.seven.mybatis.config;

import com.seven.mybatis.DynamicDataSourceProvider;
import com.seven.mybatis.DynamicRoutingDataSource;
import com.seven.mybatis.YamlDynamicDataSourceProvider;
import com.seven.mybatis.datasource.AssignDataSourceClassAspect;
import com.seven.mybatis.datasource.AssignDataSourceMethodAspect;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author shanhonghao
 * @date 2018-08-22 11:23
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DynamicDataSourceAutoConfiguration {

    private final DynamicDataSourceProperties properties;

    public DynamicDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new YamlDynamicDataSourceProvider(properties);
    }

    @Bean
    @Primary
    public DynamicRoutingDataSource dynamicRoutingDataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setProvider(dynamicDataSourceProvider);
        dynamicRoutingDataSource.setPrimary(properties.getPrimary());
        return dynamicRoutingDataSource;
    }


    /**
     * aop注解拦截切面
     */
    @Bean
    @ConditionalOnMissingBean
    public AssignDataSourceMethodAspect assignDataSourceMethodAspect() {
        return new AssignDataSourceMethodAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public AssignDataSourceClassAspect assignDataSourceClassAspect() {
        return new AssignDataSourceClassAspect();
    }

}
