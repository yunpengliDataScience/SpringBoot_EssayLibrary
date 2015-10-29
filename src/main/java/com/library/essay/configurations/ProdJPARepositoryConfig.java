package com.library.essay.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("prod")
@Configuration
@PropertySource("classpath:hibernate-prod.properties")
public class ProdJPARepositoryConfig extends JPARepositoryConfig {

}
