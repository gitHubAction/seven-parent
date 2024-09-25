package com.seven.mybatis.config.mapper;

import lombok.Generated;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.Environment;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.spring.mapper.ClassPathMapperScanner;
import tk.mybatis.spring.mapper.SpringBootBindUtil;

/**
 * tk mapper 默认只会加载 Config.java 中的 PREFIX 的配置, 这里做个替换.
 * @author zhangshihao01
 * @date 2024/9/25 17:21
 */
@Slf4j
@Generated
public class SevenClassPathMapperScanner extends ClassPathMapperScanner {

    @Getter
    boolean mapperPropertiesLoaded = false; // 用于 unit test

    public SevenClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 从环境变量中获取 mapper 配置信息
     *
     * @param environment
     */
    @Override
    public void setMapperProperties(Environment environment) {
        Config config = SpringBootBindUtil.bind(environment, MapperProperties.class, MapperProperties.PREFIX);
        MapperHelper mapperHelper = super.getMapperHelper();
        if (mapperHelper == null) {
            mapperHelper = new MapperHelper();
        }
        if(config != null){
            mapperHelper.setConfig(config);
        }

        mapperPropertiesLoaded = true;
        log.debug("{} config loaded", MapperProperties.PREFIX);
    }
}
