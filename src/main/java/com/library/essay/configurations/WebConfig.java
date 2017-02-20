package com.library.essay.configurations;

import javax.faces.webapp.FacesServlet;

import org.apache.catalina.Context;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.NonEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import com.library.essay.quartz.listeners.MyQuartzListener;
import com.library.essay.reports.servlets.ChartReportServlet;
import com.library.essay.reports.servlets.DynamicChartReportServlet;
import com.library.essay.reports.servlets.JFreeChartDemoServlet;
import com.library.essay.reports.servlets.ReportServlet;
import com.library.essay.tinymce.spellchecker.JazzySpellCheckerServlet;
import com.sun.faces.config.ConfigureListener;

@Configuration
public class WebConfig {

  // Register FacesServlet
  @Bean
  public ServletRegistrationBean faceServletRegistrationBean() {
    FacesServlet facesServlet = new FacesServlet();
    ServletRegistrationBean servletRegistrationBean =
        new ServletRegistrationBean(facesServlet, "*.xhtml");

    servletRegistrationBean.setLoadOnStartup(1);
    return servletRegistrationBean;
  }

  // Register HttpRequestHandler
//  @Bean(name = "httpRequestHandlerServlet")
//  public HttpRequestHandler reportSpringBeanServlet() {
//    return new ReportServlet();
//  }

  // Register HttpRequestHandlerServlet that maps to /report
//  @Bean
//  public ServletRegistrationBean httpRequestHandlerServletRegistrationBean() {
//
//    ServletRegistrationBean servletRegistrationBean =
//        new ServletRegistrationBean(new HttpRequestHandlerServlet(), "/report");
//
//    return servletRegistrationBean;
//  }
  
  @Bean
  public ReportServlet reportServlet() {
    return new ReportServlet();
  }
  
  @Bean
  public ChartReportServlet chartReportServlet() {
    return new ChartReportServlet();
  }
  
  @Bean
  public DynamicChartReportServlet dynamicChartReportServlet() {
    return new DynamicChartReportServlet();
  }
  
  @Bean
  public JFreeChartDemoServlet jFreeChartDemoServlet() {
    return new JFreeChartDemoServlet();
  }

  // Register Jazzy SpellChecker
  @Bean
  public ServletRegistrationBean jazzySpellCheckerServletRegistrationBean() {
    JazzySpellCheckerServlet jazzySpellCheckerServlet = new JazzySpellCheckerServlet();
    ServletRegistrationBean servletRegistrationBean =
        new ServletRegistrationBean(jazzySpellCheckerServlet, "/servlet/jazzy-spellchecker");

    servletRegistrationBean.setLoadOnStartup(2);
    return servletRegistrationBean;
  }

  // Specify the welcome file.
  @Bean
  @ConditionalOnMissingBean(NonEmbeddedServletContainerFactory.class)
  public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

    tomcat.addContextCustomizers(new TomcatContextCustomizer() {
      @Override
      public void customize(Context context) {

        context.addWelcomeFile("pages/homePage.xhtml");
      }
    });

    return tomcat;
  }

  @Bean
  public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
    return new ServletListenerRegistrationBean<ConfigureListener>(new ConfigureListener());
  }

  // Servlet Listener registration
  @Bean
  public ServletListenerRegistrationBean<MyQuartzListener> myQuartzListener() {
    return new ServletListenerRegistrationBean<MyQuartzListener>(new MyQuartzListener());
  }

  // Register OpenEntityManagerInViewFilter
  @Bean
  public FilterRegistrationBean openEntityManagerInViewFilterRegistrationBean() {

    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

    OpenEntityManagerInViewFilter openEntityManagerInViewFilter =
        new OpenEntityManagerInViewFilter();

    filterRegistrationBean.setFilter(openEntityManagerInViewFilter);
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.setOrder(1);

    return filterRegistrationBean;
  }
}
