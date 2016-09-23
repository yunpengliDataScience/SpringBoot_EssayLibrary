package com.library.essay.faces.beans.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.library.essay.security.beans.ActiveDirectoryUserDetails;

@Component("userInformationBean")
@Scope("session")
public class UserInformationBean {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private String currentPrincipalName;

  public UserInformationBean() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    currentPrincipalName = authentication.getName();

    Object principal = authentication.getPrincipal();
    if (principal instanceof ActiveDirectoryUserDetails) {
      ActiveDirectoryUserDetails activeDirectoryUserDetails =
          (ActiveDirectoryUserDetails) principal;

      logger.info("activeDirectoryUserDetails [ " + activeDirectoryUserDetails + " ]");
    }

  }

  public String getCurrentPrincipalName() {
    return currentPrincipalName;
  }

  public void setCurrentPrincipalName(String currentPrincipalName) {
    this.currentPrincipalName = currentPrincipalName;
  }

}
