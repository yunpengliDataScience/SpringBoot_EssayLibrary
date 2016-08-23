package com.library.essay.faces.beans.controllers;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("loginNavigationBean")
@Scope("session")
public class LoginNavigationBean implements Serializable {

  private static final long serialVersionUID = 1520318172495977648L;

  public String toLoginPage() {
    return "/pages/public/loginPage.xhtml";
  }

  public String toHomePage() {
    return "/pages/homePage.xhtml";
  }

}
