package com.library.essay.faces.beans.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.library.essay.utils.MyEnvironmentAware;

@Component("activeProfileBean")
@Scope("singleton")
public class ActiveProfileBean {

	@Autowired
	private EnvironmentAware myEnvironmentAware;

	public EnvironmentAware getMyEnvironmentAware() {
		return myEnvironmentAware;
	}

	public void setMyEnvironmentAware(EnvironmentAware myEnvironmentAware) {
		this.myEnvironmentAware = myEnvironmentAware;
	}

	public String getActiveProfiles() {

		return StringUtils.join(((MyEnvironmentAware) myEnvironmentAware).getEnv().getActiveProfiles(), ',');
	}
}
