package com.library.essay.faces.beans.controllers;

import org.springframework.context.annotation.Scope;

@Scope("session")
public class UserPreferences {

	private String myCurrentTheme;

	public String getMyCurrentTheme() {
		return myCurrentTheme;
	}

	public void setMyCurrentTheme(String myCurrentTheme) {
		this.myCurrentTheme = myCurrentTheme;
	}

}
