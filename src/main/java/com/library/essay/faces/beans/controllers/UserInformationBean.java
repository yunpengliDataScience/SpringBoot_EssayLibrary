package com.library.essay.faces.beans.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userInformationBean")
@Scope("session")
public class UserInformationBean {

  private String currentPrincipalName;

  public UserInformationBean() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    currentPrincipalName = authentication.getName();
  }

  public String getCurrentPrincipalName() {
    return currentPrincipalName;
  }

  public void setCurrentPrincipalName(String currentPrincipalName) {
    this.currentPrincipalName = currentPrincipalName;
  }

}
