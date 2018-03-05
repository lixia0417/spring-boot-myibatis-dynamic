package com.neo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.neo.route.RoutingDataSource;
import com.neo.utils.Constants;

@Configuration
public class DbDynamicConfiguration {
	
	@Bean("dynamicDataSource")
	DataSource dynamicDataSource(@Qualifier("defaultDataSource") DataSource defaultDataSource) {
		System.err.println("create routing datasource...");
		Map<Object, Object> map = new HashMap<>();
		map.put(Constants.DefaultDataSource, defaultDataSource);
		RoutingDataSource routing = new RoutingDataSource();
		routing.setTargetDataSources(map);
		routing.setDefaultTargetDataSource(defaultDataSource);
		return routing;
	}
	
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        return sqlSessionFactoryBean;
    }

}
