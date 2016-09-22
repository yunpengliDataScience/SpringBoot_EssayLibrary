package com.library.essay.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("jdbc-security")
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class JDBCSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	//1. Configure HTTP URL pattern mappings.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		
				.antMatchers("/javax.faces.resource/**", "/pages/public/**", "/pages/logout").permitAll()
				.antMatchers("/pages/admin/**").hasAuthority("ROLE_ADMIN")
				.antMatchers("/pages/superUser/**").hasAuthority("ROLE_SUPER")
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

	//2. Configure authentication strategies.
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT USER_NAME, PASSWORD, ENABLED FROM USER WHERE USER_NAME=?")
			.authoritiesByUsernameQuery("SELECT u.USER_NAME, ur.ROLE FROM USER u, USER_ROLE ur WHERE u.USER_ID = ur.USER_ID AND u.USER_NAME =?");
	}
}
