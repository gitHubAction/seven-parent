package com.seven.mybatis.datasource;

import com.seven.mybatis.toolkit.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

/**
 * 指定数据源AOP
 * @author zhangshihao01
 * @date 2024/9/25 17:33
 */
@Aspect
@Order(-5)
@Slf4j
public class AssignDataSourceMethodAspect {

    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint point, AssignDataSource targetDataSource) {
        String dataSourceName = targetDataSource.value();
        DataSourceContextHolder.switchTo(dataSourceName);
        log.debug("切换数据源: [{}]", dataSourceName);
    }

    @After("@annotation(targetDataSource)")
    public void restoreDataSource(JoinPoint point, AssignDataSource targetDataSource) {
        DataSourceContextHolder.clear();
        log.debug("回收数据源: [{}] > [{}]", targetDataSource.value(), point.getSignature());
    }

}
