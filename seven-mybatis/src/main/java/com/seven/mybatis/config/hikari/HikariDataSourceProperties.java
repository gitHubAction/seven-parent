package com.seven.mybatis.config.hikari;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/25 16:20
 */
@Getter
@Setter
public class HikariDataSourceProperties {
    /**
     * 控制从池返回的连接的默认自动提交行为。默认值：true
     */
    private Boolean autoCommit = true;
    /**
     * 客户端等待连接的最大毫秒数。如果在没有可用连接的情况下超过此时间，则会抛出SQLException。默认值：30000
     */
    private long connectionTimeout = 30000;
    /**
     * 此属性控制允许连接在池中闲置的最长时间。此设置仅适用于minimumIdle定义为小于maximumPoolSize。默认值：600000（10分钟）
     */
    private long idleTimeout = 600000;
    /**
     * 此属性控制池中连接的最大生存期。默认值：1800000（30分钟）
     */
    private long maxLifetime = 1800000;
    /**
     * 该属性控制HikariCP尝试在池中维护的最小空闲连接数。默认值：与maximumPoolSize相同
     */
    private Integer minimumIdle = 10;
    /**
     * 此属性控制池允许达到的最大大小，包括空闲和正在使用的连接。默认值：10
     */
    private Integer maximumPoolSize = 10;
}
