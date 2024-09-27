package com.seven.mybatis;

import com.seven.mybatis.toolkit.DataSourceContextHolder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/25 16:07
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource{

    /**
     * 所有库
     */
    @Getter
    private Map<String, DataSource> dataSourceMap;

    @Autowired
    private DefaultListableBeanFactory beanFactory;

    @Setter
    private DynamicDataSourceProvider provider;

    /**
     * 默认数据源名称，默认master，可为组数据源名，可为单数据源名
     */
    @Setter
    private String primary;

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        String lookupKey = (String) determineCurrentLookupKey();
        if (dataSourceMap.containsKey(lookupKey)) {
            log.debug("从 [{}] 单数据源中返回数据源", lookupKey);
            return dataSourceMap.get(lookupKey);
        }
        log.debug("从默认数据源中返回数据 [{}]", primary);
        return dataSourceMap.get(primary);
    }

    @Override
    public void afterPropertiesSet() {
        this.dataSourceMap = provider.loadDataSources();
        this.dataSourceMap.forEach((name, dataSource) -> {
            String beanName = name + "DataSource";
            beanFactory.registerSingleton(beanName, dataSource);
            log.debug("register datasource bean [{}] as [{}]", dataSource.getClass().getSimpleName(), beanName);
        });
        log.debug("共加载 [{}] 个数据源", dataSourceMap.size());
        //检测默认数据源设置
        if (dataSourceMap.containsKey(primary)) {
            log.debug("当前的默认数据源是单数据源, 数据源名为 [{}]", primary);
        } else {
            throw new RuntimeException("请检查 primary 默认数据库设置, 当前未找到 [" + primary + "] 数据源");
        }

    }

    @Override
    public Map<Object, DataSource> getResolvedDataSources() {
        Assert.state(this.dataSourceMap != null, "DataSources not resolved yet - call afterPropertiesSet");
        return Collections.unmodifiableMap(this.dataSourceMap);
    }

    /**
     * 动态添加数据源
     * @param name
     * @param dataSource
     * @return void
     * @author zhangshihao01
     * @date 2024/9/25 17:57
     */
    public synchronized void addDataSource(String name, DataSource dataSource){
        Assert.state(this.dataSourceMap != null, "DataSources not resolved yet - call afterPropertiesSet");
        if(!this.dataSourceMap.containsKey(name)){
            DataSource oldDatasource = this.dataSourceMap.put(name, dataSource);
            //关闭老的数据源

            beanFactory.registerSingleton(name + "DataSource", dataSource);
        }
    }

    public synchronized boolean removeDataSource(String name){
        if (primary.equals(name)) {
            throw new RuntimeException("could not remove primary datasource");
        }
        if(!this.dataSourceMap.containsKey(name)){
            return false;
        }
        DataSource existDatasource = this.dataSourceMap.remove(name);
        //关闭老的数据源
        beanFactory.removeBeanDefinition(name + "DataSource");
        return existDatasource == null;
    }
}
