package com.library.essay.configurations;

import java.util.Collection;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import com.library.essay.security.beans.ActiveDirectoryUserDetails;

@Profile("activeDirectory-security")
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ActiveDirectorySecurityConfig extends WebSecurityConfigurerAdapter {

  // 1. Configure HTTP URL pattern mappings.
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/javax.faces.resource/**", "/pages/public/**", "/pages/logout").permitAll()
        // .antMatchers("/pages/admin/**").hasAuthority("ROLE_ADMIN")
        // .antMatchers("/pages/superUser/**").hasAuthority("ROLE_SUPER")
        .anyRequest().authenticated().and().formLogin().loginPage("/pages/public/loginPage.xhtml")
        .and().csrf().disable().logout().logoutUrl("/pages/logout")
        .logoutSuccessUrl("/pages/public/loginPage.xhtml").invalidateHttpSession(true);
  }

  @Bean
  public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {

    String domain = "rootlnka.net";
    String url = "ldap://hldap.goldlnk.rootlnka.net:389";
    String rootDN = "dc=goldlnk,dc=rootlnka,dc=net";


    ActiveDirectoryLdapAuthenticationProvider provider =
        new ActiveDirectoryLdapAuthenticationProvider(domain, url, rootDN);

    // String searchFilter = "(&(objectClass=user)(sAMAccountName={0}))";
    // provider.setSearchFilter(searchFilter);

    provider.setConvertSubErrorCodesToExceptions(true);
    provider.setUseAuthenticationRequestCredentials(true);

    // Inject customized UserDetailsContextMapper to capture more user detail info.
    provider.setUserDetailsContextMapper(activeDirectoryUserDetailsContextMapper());

    return provider;
  }

  /**
   * 
   * Customized UserDetailsContextMapper based on LdapUserDetailsMapper to capture additional user
   * attributes upon authentication against Active Directory
   */
  @Bean
  public UserDetailsContextMapper activeDirectoryUserDetailsContextMapper() {

    // LdapUserDetailsMapper is the base class for mapping user info from directory context.
    return new LdapUserDetailsMapper() {

      @Override
      public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
          Collection<? extends GrantedAuthority> authorities) {

        LdapUserDetails ldapUserDetails =
            (LdapUserDetails) super.mapUserFromContext(ctx, username, authorities);

        // ActiveDirectoryUserDetails is the wraper of LdapUserDetails and contains extra user
        // attributes.
        ActiveDirectoryUserDetails activeDirectoryUserDetails =
            new ActiveDirectoryUserDetails(ldapUserDetails);

        String email = ctx.getStringAttribute("mail");
        String title = ctx.getStringAttribute("title");
        String sAMAccountName = ctx.getStringAttribute("sAMAccountName");
        String company = ctx.getStringAttribute("company");
        String displayName = ctx.getStringAttribute("displayName");
        String userPrincipalName = ctx.getStringAttribute("userPrincipalName");
        String telephoneNumber = ctx.getStringAttribute("telephoneNumber");
        String streetAddress = ctx.getStringAttribute("streetAddress");
        String state = ctx.getStringAttribute("st");
        String location = ctx.getStringAttribute("l");

        activeDirectoryUserDetails.setEmail(email);
        activeDirectoryUserDetails.setTitle(title);
        activeDirectoryUserDetails.setsAMAccountName(sAMAccountName);
        activeDirectoryUserDetails.setCompany(company);
        activeDirectoryUserDetails.setDisplayName(displayName);
        activeDirectoryUserDetails.setUserPrincipalName(userPrincipalName);
        activeDirectoryUserDetails.setTelephoneNumber(telephoneNumber);
        activeDirectoryUserDetails.setState(state);
        activeDirectoryUserDetails.setStreetAddress(streetAddress);
        activeDirectoryUserDetails.setLocation(location);

        return activeDirectoryUserDetails;
      }

      @Override
      public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        super.mapUserToContext(user, ctx);
      }

    };
  }

  // 2. Configure authentication strategies.
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
  }

}
