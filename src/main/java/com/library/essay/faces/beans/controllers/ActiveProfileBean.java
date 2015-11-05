package com.library.essay.faces.beans.controllers;

import javax.annotation.ManagedBean;
import javax.faces.bean.ApplicationScoped;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;

import com.library.essay.utils.MyEnvironmentAware;

@ManagedBean("activeProfileBean")
@ApplicationScoped
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
