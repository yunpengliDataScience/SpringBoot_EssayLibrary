package com.library.essay.faces.beans.controllers;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.library.essay.persistence.entities.User;

@Component("userBean")
@Scope("session")
public class UserBean implements Serializable {

  private User signedInUser;

  public User getSignedInUser() {
    return signedInUser;
  }

  public void setSignedInUser(User signedInUser) {
    this.signedInUser = signedInUser;
  }

}
