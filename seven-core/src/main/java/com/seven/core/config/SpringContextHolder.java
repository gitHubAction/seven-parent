package com.seven.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/25 14:40
 */
@Component("SpringContextHolder")
@Lazy(value = false)
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        ApplicationContext applicationContext = getApplicationContext();
        return applicationContext == null ? null : applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        ApplicationContext applicationContext = getApplicationContext();
        return applicationContext == null ? null : applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        ApplicationContext applicationContext = getApplicationContext();
        return applicationContext == null ? null : applicationContext.getBean(name, clazz);
    }
}
