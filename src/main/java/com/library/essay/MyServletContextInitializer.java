package com.library.essay;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

@Configuration
public class MyServletContextInitializer implements ServletContextInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
		servletContext.setInitParameter("primefaces.THEME", "sunny");
		servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/springsecurity.taglib.xml");
		
		// Register ReportServlet as a servlet.
	    // "reportServlet" should match the method name annotated with @Bean.
	    ServletRegistration.Dynamic reportServletRegistration =
	        servletContext.addServlet("reportServlet", new HttpRequestHandlerServlet());
	    reportServletRegistration.addMapping("/report");
	    
	    ServletRegistration.Dynamic chartReportServletRegistration =
            servletContext.addServlet("chartReportServlet", new HttpRequestHandlerServlet());
	    chartReportServletRegistration.addMapping("/chartReport");
	    
	    ServletRegistration.Dynamic dynamicChartReportServletRegistration =
            servletContext.addServlet("dynamicChartReportServlet", new HttpRequestHandlerServlet());
	    dynamicChartReportServletRegistration.addMapping("/dynamicChartReport");
	    
	}

}
