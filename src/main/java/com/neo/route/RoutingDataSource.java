package com.neo.route;

import java.sql.SQLException;

import org.springframework.util.StringUtils;

import com.alibaba.druid.pool.DruidDataSource;

public class RoutingDataSource extends AbstractDynamicDataSource<DruidDataSource> {

    private boolean testWhileIdle = true;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;

    // 是否打开连接泄露自动检测
    private boolean removeAbandoned = false;
    // 连接长时间没有使用，被认为发生泄露时长
    private long removeAbandonedTimeoutMillis = 300 * 1000;
    // 发生泄露时是否需要输出 log，建议在开启连接泄露检测时开启，方便排错
    private boolean logAbandoned = false;
    // 配置监控统计拦截的filters
    private String filters = "stat" ; // 监控统计："stat" 防SQL注入："wall" 组合使用： "stat,wall"

    /*
     * 创建数据源
     */
    @Override
    public DruidDataSource createDataSource(String driverClassName, String url, String username,
                                            String password) {
        DruidDataSource parent = (DruidDataSource) super.getApplicationContext().getBean(
            DEFAULT_DATASOURCE_KEY);
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setInitialSize(parent.getInitialSize());
        ds.setMinIdle(parent.getMinIdle());
        ds.setMaxActive(parent.getMaxActive());
        ds.setMaxWait(parent.getMaxWait());
        ds.setTimeBetweenConnectErrorMillis(parent.getTimeBetweenConnectErrorMillis());
        ds.setTimeBetweenEvictionRunsMillis(parent.getTimeBetweenEvictionRunsMillis());
        ds.setMinEvictableIdleTimeMillis(parent.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(parent.getValidationQuery());
        ds.setTestWhileIdle(testWhileIdle);
        ds.setTestOnBorrow(testOnBorrow);
        ds.setTestOnReturn(testOnReturn);
        ds.setRemoveAbandoned(removeAbandoned);
        ds.setRemoveAbandonedTimeoutMillis(removeAbandonedTimeoutMillis);
        ds.setLogAbandoned(logAbandoned);
        // 只要maxPoolPreparedStatementPerConnectionSize>0,poolPreparedStatements就会被自动设定为true，参照druid的源码
        ds.setMaxPoolPreparedStatementPerConnectionSize(parent
            .getMaxPoolPreparedStatementPerConnectionSize());
        if (!StringUtils.isEmpty(filters)){
        	try {
        		ds.setFilters(filters);
        	} catch (SQLException e) {
        		throw new RuntimeException(e);
        	}        	
        }
        return ds;
    }

}
