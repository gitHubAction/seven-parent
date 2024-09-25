package com.seven.mybatis.config.typehandler;

/**
 * value 为 int 的枚举
 * @author zhangshihao01
 * @date 2024/9/25 17:28
 */
public interface BaseIntEnum {
    // 因为mvc 不管收到什么类型的数据, 都会先处理为 string, 所以这里必须为 string
    String getValue();
}
