package com.library.essay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import com.library.essay.configurations.JPARepositoryConfig;

//Mark the class to be spring configuration equivalent to applicationContext.xml
@Configuration
@ComponentScan(basePackages = { "com.library.essay" })
public class TestConfiguration extends JPARepositoryConfig {

	@Bean
	public DatabaseConfigBean dbUnitDatabaseConfig() {
		DatabaseConfigBean dbConfig = new com.github.springtestdbunit.bean.DatabaseConfigBean();
		dbConfig.setDatatypeFactory(new org.dbunit.ext.h2.H2DataTypeFactory());
		return dbConfig;
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() {
		DatabaseDataSourceConnectionFactoryBean dbConnection = new com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean(
				dataSource());
		dbConnection.setDatabaseConfig(dbUnitDatabaseConfig());
		return dbConnection;
	}
}
