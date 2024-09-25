package com.seven.mybatis.datasource;

import java.lang.annotation.*;

/**
 * 用于指定数据源的注解
 * @author zhangshihao01
 * @date 2024/9/25 17:32
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AssignDataSource {

    String value();

}
