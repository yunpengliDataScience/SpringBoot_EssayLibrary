package com.library.essay.configurations;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

//Create ApplicationContextInitializer to initialize Spring application context during test; otherwise, ${spring.profiles.active} cannot be resolved in a stand-alone test environment.
@Deprecated
public class TestContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {

	public void initialize(ConfigurableApplicationContext context) {
		context.getEnvironment().getSystemProperties()
				.put(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test");

	}
}
