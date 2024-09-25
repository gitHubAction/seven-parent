package com.seven.mybatis;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @Date 2024/9/25 16:09
 */
public interface DynamicDataSourceProvider {

    Map<String, DataSource> loadDataSources();
}
