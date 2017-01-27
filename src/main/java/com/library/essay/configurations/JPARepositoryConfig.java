package com.library.essay.configurations;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.library.essay.utils.MyEnvironmentAware;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
//Load property file based on Profile specified
//@PropertySource("classpath:hibernate-${spring.profiles.active}.properties")
//Enable Spring transaction management
@EnableTransactionManagement
//Specify JPA repository location
@EnableJpaRepositories(basePackages = { "com.library.essay.persistence.repositories" })
public class JPARepositoryConfig {

	// Inject environment variable to be used to load properties from the
		// property file
		@Resource
		private Environment environment;

		// Datasource bean definition
		@Bean(destroyMethod = "close")
		public DataSource dataSource() {
			HikariConfig dataSourceConfig = new HikariConfig();
			dataSourceConfig.setDriverClassName(environment.getRequiredProperty("jdbc.driver"));
			dataSourceConfig.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
			dataSourceConfig.setUsername(environment.getRequiredProperty("jdbc.username"));
			dataSourceConfig.setPassword(environment.getRequiredProperty("jdbc.password"));

			return new HikariDataSource(dataSourceConfig);
		}

		// JpaTransactionManager Bean
		@Bean
		public JpaTransactionManager transactionManager() throws ClassNotFoundException {
			JpaTransactionManager transactionManager = new JpaTransactionManager();

			transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

			return transactionManager;
		}

		@Bean
		public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException {
			LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

			entityManagerFactoryBean.setDataSource(dataSource());

			entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
			entityManagerFactoryBean.setPackagesToScan("com.library.essay.persistence.entities");

			Properties jpaProperties = new Properties();

			// Configures the used database dialect. This allows Hibernate to create
			// SQL that is optimized for the used database.
			jpaProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));

			// Specifies the action that is invoked to the database when the
			// Hibernate SessionFactory is created or closed.
			jpaProperties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));

			// Configures the naming strategy that is used when Hibernate creates
			// new database objects and schema elements
			jpaProperties.put("hibernate.ejb.naming_strategy",
					environment.getRequiredProperty("hibernate.ejb.naming_strategy"));

			// If the value of this property is true, Hibernate writes all SQL
			// statements to the console.
			jpaProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

			// If the value of this property is true, Hibernate will format the SQL
			// that is written to the console.
			jpaProperties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));

			// Hibernate Search properties
		    jpaProperties.put("hibernate.search.default.directory_provider",
		        environment.getRequiredProperty("hibernate.search.default.directory_provider"));

		    jpaProperties.put("hibernate.search.default.indexBase",
		        environment.getRequiredProperty("hibernate.search.default.indexBase"));
		    
			entityManagerFactoryBean.setJpaProperties(jpaProperties);

			return entityManagerFactoryBean;
		}
		
		@Bean
		public MyEnvironmentAware myEnvironmentAware(){
			return new MyEnvironmentAware();
		}
}
