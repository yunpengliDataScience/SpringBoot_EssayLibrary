package com.library.essay.faces.beans.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.library.essay.persistence.entities.User;
import com.library.essay.persistence.entities.UserRole;
import com.library.essay.services.UserService;
import com.library.essay.utils.SessionUtils;

@Component("loginBean")
@Scope("session")
public class LoginBean implements Serializable {

  private static final long serialVersionUID = 7765876811740798583L;

  private String username;

  private String password;

  @Autowired
  private LoginNavigationBean loginNavigationBean;

  @Autowired
  private UserBean userBean;

  @Autowired
  private UserService userService;

  public boolean hasRole(String role) {
    boolean hasRole = false;

    if (userBean != null && role != null) {
      User user = userBean.getSignedInUser();

      List<UserRole> roles = user.getRoles();
      for (UserRole r : roles) {
        if (role.equals(r.getRole())) {
          hasRole = true;
          break;
        }
      }
    }
    return hasRole;
  }

  public String doLogin() {


    User user = userService.authenticate(username, password);
    if (user != null) {
      userBean.setSignedInUser(user);
      return loginNavigationBean.toHomePage();
    }

    // Set login ERROR
    FacesMessage msg = new FacesMessage("Login error!", "Authentication failed!");
    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    FacesContext.getCurrentInstance().addMessage(null, msg);

    // To to login page
    return loginNavigationBean.toLoginPage();

  }

  public String doLogout() {

    HttpSession session = SessionUtils.getSession();
    session.invalidate();

    // Set logout message
    FacesMessage msg = new FacesMessage("Logout success!", "User has successfully logged out.");
    msg.setSeverity(FacesMessage.SEVERITY_INFO);
    FacesContext.getCurrentInstance().addMessage(null, msg);

    return loginNavigationBean.toLoginPage();
  }

  public boolean isLoggedIn() {
    boolean isLoggedIn = false;

    if (userBean != null) {
      if (userBean.getSignedInUser() != null) {
        isLoggedIn = true;
      }
    }

    return isLoggedIn;
  }

  // Getters & Setters
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserBean getUserBean() {
    return userBean;
  }

  public void setUserBean(UserBean userBean) {
    this.userBean = userBean;
  }

  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public LoginNavigationBean getLoginNavigationBean() {
    return loginNavigationBean;
  }

  public void setLoginNavigationBean(LoginNavigationBean loginNavigationBean) {
    this.loginNavigationBean = loginNavigationBean;
  }

}
