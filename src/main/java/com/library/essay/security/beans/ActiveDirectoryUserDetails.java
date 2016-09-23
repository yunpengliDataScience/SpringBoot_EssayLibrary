package com.library.essay.security.beans;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

/**
 * This class is used to capture extra user attributes from Active Directory upon authentication
 * 
 * @author yunpeng.li
 *
 */
public class ActiveDirectoryUserDetails implements LdapUserDetails {

  // Extra attributes from Active Directory
  private String email;
  private String title;
  private String sAMAccountName;
  private String company;
  private String displayName;
  private String userPrincipalName;
  private String telephoneNumber;
  private String streetAddress;
  private String state; // st
  private String location; // l

  private LdapUserDetails ldapUserDetails;

  public ActiveDirectoryUserDetails(LdapUserDetails ldapUserDetailsImpl) {
    this.ldapUserDetails = ldapUserDetailsImpl;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return ldapUserDetails.getAuthorities();
  }

  @Override
  public String getPassword() {

    return ldapUserDetails.getPassword();
  }

  @Override
  public String getUsername() {

    return ldapUserDetails.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {

    return ldapUserDetails.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {

    return ldapUserDetails.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return ldapUserDetails.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {

    return ldapUserDetails.isEnabled();
  }

  @Override
  public String getDn() {

    return ldapUserDetails.getDn();
  }

  public String getsAMAccountName() {
    return sAMAccountName;
  }

  public void setsAMAccountName(String sAMAccountName) {
    this.sAMAccountName = sAMAccountName;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getUserPrincipalName() {
    return userPrincipalName;
  }

  public void setUserPrincipalName(String userPrincipalName) {
    this.userPrincipalName = userPrincipalName;
  }

  public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public LdapUserDetails getLdapUserDetails() {
    return ldapUserDetails;
  }

  public void setLdapUserDetails(LdapUserDetails ldapUserDetails) {
    this.ldapUserDetails = ldapUserDetails;
  }

  @Override
  public String toString() {
    return "Dn=" + this.getDn() + ", displayName=" + this.displayName + ", company=" + this.company
        + ", email=" + this.email + ", sAMAccountName=" + this.sAMAccountName + ", telephoneNumber="
        + this.telephoneNumber + ", title=" + this.title + ", userPrincipalName="
        + this.userPrincipalName + ", streetAddress=" + this.streetAddress + ", location="
        + this.location + ", state=" + this.state;
  }

}
