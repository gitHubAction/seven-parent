package com.seven.mybatis.config.durid;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/25 16:19
 */
@Getter
@Setter
public class DruidWallConfigProperties {
    private boolean noneBaseStatementAllow;

    private boolean multiStatementAllow;
}
