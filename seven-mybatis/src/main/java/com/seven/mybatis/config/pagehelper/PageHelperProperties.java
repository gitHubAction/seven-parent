package com.seven.mybatis.config.pagehelper;

import lombok.Data;
import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;
import java.util.Properties;

/**
 * Configuration properties for PageHelper.
 *
 * @author shanhonghao
 * @date 2018-10-08 15:58
 * @since 1.0.2
 */
@ConfigurationProperties(prefix = PageHelperProperties.PAGEHELPER_PREFIX)
@Generated
@Data
public class PageHelperProperties {

    public static final String PAGEHELPER_PREFIX = "longfor.data.pagehelper";

    /**
     * offsetAsPageNum: 默认值为 false.
     * 该参数对使用 RowBounds 作为分页参数时有效.
     * 当该参数设置为 true 时, 会将 RowBounds 中的 offset 参数当成 pageNum 使用, 可以用页码和页面大小两个参数进行分页.
     */
    private Boolean offsetAsPageNum;
    /**
     * rowBoundsWithCount: 默认值为false.
     * 该参数对使用 RowBounds 作为分页参数时有效.
     * 当该参数设置为true时, 使用 RowBounds 分页会进行 count 查询.
     */
    private Boolean rowBoundsWithCount;
    /**
     * pageSizeZero: 默认值为 false.
     * 当该参数设置为 true 时, 如果 pageSize=0 或者 RowBounds.limit=0 就会查询出全部的结果
     * 相当于没有执行分页查询, 但是返回结果仍然是 Page 类型.
     */
    private Boolean pageSizeZero;
    /**
     * reasonable: 分页合理化参数, 默认值为false.
     * 当该参数设置为 true 时, pageNum <= 0 时会查询第一页, pageNum > pages(超过总数时), 会查询最后一页
     * 默认false 时, 直接根据参数进行查询.
     */
    private Boolean reasonable;
    /**
     * supportMethodsArguments: 支持通过 Mapper 接口参数来传递分页参数, 默认值 false.
     * 分页插件会从查询方法的参数值中, 自动根据上面 params 配置的字段中取值, 查找到合适的值时就会自动分页.
     * 使用方法可以参考测试代码中的 com.github.pagehelper.test.basic 包下的 ArgumentsMapTest 和 ArgumentsObjTest.
     */
    private Boolean supportMethodsArguments;
    /**
     * dialect: 默认情况下会使用 PageHelper 方式进行分页,
     * 如果想要实现自己的分页逻辑, 可以实现 Dialect(com.github.pagehelper.Dialect) 接口, 然后配置该属性为实现类的全限定名称.
     */
    private String dialect;
    /**
     * helperDialect: 分页插件会自动检测当前的数据库链接, 自动选择合适的分页方式.
     * 你可以配置 helperDialect 属性来指定分页插件使用哪种方言. 配置时, 可以使用下面的缩写值:
     * oracle, mysql, mariadb, sqlite, hsqldb, postgresql, db2, sqlserver, informix, h2, sqlserver2012, derby
     * 特别注意: 使用 SqlServer2012 数据库时, 需要手动指定为 sqlserver2012, 否则会使用 SqlServer2005 的方式进行分页.
     * 你也可以实现 AbstractHelperDialect, 然后配置该属性为实现类的全限定名称即可使用自定义的实现方法.
     */
    private String helperDialect;

    /**
     * autoRuntimeDialect: 默认值为 false.
     * 设置为 true 时, 允许在运行时根据多数据源自动识别对应方言的分页 (不支持自动选择 sqlserver2012, 只能使用 sqlserver).
     */
    private Boolean autoRuntimeDialect;
    /**
     * closeConn: 默认值为 true.
     * 当使用运行时动态数据源或没有设置 helperDialect 属性自动获取数据库类型时, 会自动获取一个数据库连接.
     * 通过该属性来设置是否关闭获取的这个连接, 默认 true 关闭，设置为 false 后, 不会关闭获取的连接.
     * 这个参数的设置要根据自己选择的数据源来决定.
     */
    private Boolean closeConn;
    /**
     * params: 为了支持 startPage(Object params) 方法, 增加了该参数来配置参数映射, 用于从对象中根据属性名取值.
     * 可以配置 pageNum, pageSize, count, pageSizeZero, reasonable.
     * 不配置映射的用默认值, 默认值为 pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero.
     */
    private String params;

    public Properties toProperties() {
        Properties properties = new Properties();
        Optional.ofNullable(offsetAsPageNum).ifPresent(prop -> properties.put("offsetAsPageNum", prop));
        Optional.ofNullable(rowBoundsWithCount).ifPresent(prop -> properties.put("rowBoundsWithCount", prop));
        Optional.ofNullable(pageSizeZero).ifPresent(prop -> properties.put("pageSizeZero", prop));
        Optional.ofNullable(reasonable).ifPresent(prop -> properties.put("reasonable", prop));
        Optional.ofNullable(supportMethodsArguments).ifPresent(prop -> properties.put("supportMethodsArguments", prop));
        Optional.ofNullable(dialect).ifPresent(prop -> properties.put("dialect", prop));
        Optional.ofNullable(helperDialect).ifPresent(prop -> properties.put("helperDialect", prop));
        Optional.ofNullable(autoRuntimeDialect).ifPresent(prop -> properties.put("autoRuntimeDialect", prop));
        Optional.ofNullable(closeConn).ifPresent(prop -> properties.put("closeConn", prop));
        Optional.ofNullable(params).ifPresent(prop -> properties.put("params", prop));

        return properties;
    }

}
