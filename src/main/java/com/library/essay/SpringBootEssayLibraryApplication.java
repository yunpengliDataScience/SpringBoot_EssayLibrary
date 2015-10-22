package com.library.essay;

import java.util.Properties;

import javax.annotation.Resource;
import javax.faces.webapp.FacesServlet;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.NonEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;

import com.library.essay.reports.servlets.ReportServlet;
import com.library.essay.tinymce.spellchecker.JazzySpellCheckerServlet;
import com.sun.faces.config.ConfigureListener;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//Mark the class to be spring configuration equivalent to applicationContext.xml
@Configuration
// Load property file based on Profile specified
// @PropertySource("classpath:hibernate-${spring.profiles.active}.properties")
@PropertySource("classpath:hibernate-dev.properties")
// Enable Spring transaction management
@EnableTransactionManagement
// Specify JPA repository location
@EnableJpaRepositories(basePackages = { "com.library.essay.persistence.repositories" })
// Scan @Service and @Component definitions
@ComponentScan(basePackages = { "com.library.essay" })
@EnableAutoConfiguration
public class SpringBootEssayLibraryApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEssayLibraryApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean faceServletRegistrationBean() {
		FacesServlet facesServlet = new FacesServlet();
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(facesServlet, "*.jsf", "/faces/*",
				"*.faces", "*.xhtml");

		return servletRegistrationBean;
	}

	// Register HttpRequestHandler
	@Bean(name = "httpRequestHandlerServlet")
	public HttpRequestHandler reportSpringBeanServlet() {
		return new ReportServlet();
	}

	// Register HttpRequestHandlerServlet that maps to /resport
	@Bean
	public ServletRegistrationBean httpRequestHandlerServletRegistrationBean() {

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new HttpRequestHandlerServlet(),
				"/report");

		return servletRegistrationBean;
	}

	// Register Jazzy SpellChecker
	@Bean
	public ServletRegistrationBean jazzySpellCheckerServletRegistrationBean() {
		JazzySpellCheckerServlet jazzySpellCheckerServlet = new JazzySpellCheckerServlet();
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(jazzySpellCheckerServlet,
				"/servlet/jazzy-spellchecker");

		return servletRegistrationBean;
	}

	// Specify the welcome file.
	@Bean
	@ConditionalOnMissingBean(NonEmbeddedServletContainerFactory.class)
	public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

		tomcat.addContextCustomizers(new TomcatContextCustomizer() {
			@Override
			public void customize(Context context) {

				context.addWelcomeFile("homePage.xhtml");
			}
		});

		return tomcat;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application
				.sources(new Class[] { SpringBootEssayLibraryApplication.class, MyServletContextInitializer.class });
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
	}

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

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}
}
