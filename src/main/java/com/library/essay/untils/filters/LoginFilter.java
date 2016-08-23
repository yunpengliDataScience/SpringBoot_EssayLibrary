package com.library.essay.untils.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.library.essay.faces.beans.controllers.LoginBean;


public class LoginFilter implements Filter {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

    logger.info("LoginFilter init() is called!");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    logger.info("LoginFilter doFilter() is called!");
   
    LoginBean loginBean =
        (LoginBean) ((HttpServletRequest) request).getSession().getAttribute("loginBean");

    HttpServletRequest reqt = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    HttpSession ses = reqt.getSession(false);

    String reqURI = reqt.getRequestURI();

    logger.info("reqURI: " + reqURI);
    
    // For the first application request there is no loginBean in the session so user needs to log
    // in
    // For other requests loginBean is present but we need to check if user has logged in
    // successfully

    if (reqURI.indexOf("/loginPage.xhtml") >= 0 
        || (loginBean != null && loginBean.isLoggedIn()) || reqURI.indexOf("/public/") >= 0
        || reqURI.contains("javax.faces.resource")) {
      chain.doFilter(request, response);
    } else {
      resp.sendRedirect(reqt.getContextPath() + "/pages/public/loginPage.xhtml");
    }


  }

  @Override
  public void destroy() {
    logger.info("LoginFilter destroy() is called!");
  }

}
