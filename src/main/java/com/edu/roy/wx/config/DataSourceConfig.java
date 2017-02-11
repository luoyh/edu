package com.edu.roy.wx.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
//@PropertySource
@MapperScan(basePackages = "com.edu.roy.wx.dao", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
	
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String url;
	
	@Bean
	public DataSource dataSource() {
		HikariConfig hc = new HikariConfig();
		hc.setDriverClassName(driverClassName);
		hc.setUsername(username);
		hc.setPassword(password);
		hc.setJdbcUrl(url);
		hc.setMinimumIdle(5);
		hc.setMaximumPoolSize(10);
		hc.setConnectionTestQuery("select 1");
		return new HikariDataSource(hc);
//		return DataSourceBuilder.create()
//		.driverClassName(driverClassName)
//		.username(username)
//		.password(password)
//		.url(url).build();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		
		

		//PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		//sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("conf/mybatis-config.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("com.edu.roy.wx.model,com.edu.roy.wx.vo");

		return sqlSessionFactoryBean.getObject();
	}
	
}
