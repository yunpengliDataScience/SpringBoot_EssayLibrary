package com.library.essay.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("dev")
@Configuration
@PropertySource("classpath:hibernate-dev.properties")
public class DevJPARepositoryConfig extends JPARepositoryConfig {

}
