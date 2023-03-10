/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/
package io.entframework.kernel.db.api.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * 数据库数据源配置
 * </p>
 * <p>
 * 说明:类中属性包含默认值的不要在这里修改,应该在"application.yml"中配置
 * </p>
 *
 * @date 2017/5/21 11:18
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {

    /**
     * 数据源名称，非druid的官方配置
     */
    private String dataSourceName;

    /**
     * 连接数据库的url，不同数据库不一样。 例如： mysql : jdbc:mysql://10.20.153.104:3306/druid2 oracle :
     * jdbc:oracle:thin:@10.20.149.85:1521:ocnauto
     */
    private String url;

    /**
     * 连接数据库的用户名
     */
    private String username;

    /**
     * 连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter。
     * 详细看这里:https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter
     */
    private String password;

    /**
     * 这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName
     */
    private String driverClassName;

    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    private Integer initialSize = 2;

    /**
     * 最大连接池数量
     */
    private Integer maxActive = 20;

    /**
     * 最小连接池数量
     */
    private Integer minIdle = 1;

    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private Integer maxWait = 60000;

    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private Boolean poolPreparedStatements = true;

    /**
     * 要启用PSCache，必须配置大于0，可以配置-1关闭 当大于0时，poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    private Integer maxPoolPreparedStatementPerConnectionSize = 100;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     */
    private String validationQuery;

    /**
     * 单位：秒，检测连接是否有效的超时时间。底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法
     */
    private Integer validationQueryTimeout = 10;

    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private Boolean testOnBorrow = true;

    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private Boolean testOnReturn = true;

    /**
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private Boolean testWhileIdle = true;

    /**
     * 连接池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作。
     */
    private Boolean keepAlive = false;

    /**
     * 有两个含义： 1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于 minEvictableIdleTimeMillis 则关闭物理连接。 2)
     * testWhileIdle 的判断依据，详细看 testWhileIdle 属性的说明
     */
    private Integer timeBetweenEvictionRunsMillis = 60000;

    /**
     * 连接保持空闲而不被驱逐的最小时间
     */
    private Integer minEvictableIdleTimeMillis = 300000;

    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有： 监控统计用的filter:stat 日志用的filter:log4j
     * 防御sql注入的filter:wall
     */
    private String filters = "stat";

}
