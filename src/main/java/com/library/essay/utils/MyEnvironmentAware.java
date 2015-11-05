package com.library.essay.utils;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class MyEnvironmentAware implements EnvironmentAware {

	private Environment env = null;

	@Override
	public void setEnvironment(Environment environment) {
		env = environment;
		// log the stuff you want here

		System.out.println("====== Active Profiles======");

		for (String activeProfile : env.getActiveProfiles()) {
			System.out.println(activeProfile);
		}
		System.out.println("============================");
	}

	public Environment getEnv() {
		return env;
	}
	
}
