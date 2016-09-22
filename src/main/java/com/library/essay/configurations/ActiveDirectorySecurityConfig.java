package com.library.essay.configurations;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@Profile("activeDirectory-security")
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ActiveDirectorySecurityConfig extends WebSecurityConfigurerAdapter {

  // 1. Configure HTTP URL pattern mappings.
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
    .authorizeRequests()
    .antMatchers("/javax.faces.resource/**", "/pages/public/**", "/pages/logout").permitAll()
    //.antMatchers("/pages/admin/**").hasAuthority("ROLE_ADMIN")
    //.antMatchers("/pages/superUser/**").hasAuthority("ROLE_SUPER")
    .anyRequest().authenticated()
    .and()
    .formLogin().loginPage("/pages/public/loginPage.xhtml")
    .and()
    .csrf().disable()
    .logout()
        .logoutUrl("/pages/logout")
        .logoutSuccessUrl("/pages/public/loginPage.xhtml")
        .invalidateHttpSession(true);
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
    
    return provider;
  }

  // 2. Configure authentication strategies.
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
  }

}
