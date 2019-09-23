package com.feritoth.ticketingsystem.configuration.rest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		/* Create the annotation configuration context and use it for configuring the ServletContext passed in as parameter */
		AnnotationConfigWebApplicationContext configContext = new AnnotationConfigWebApplicationContext();
		servletContext.addListener(new ContextLoaderListener(configContext));
		servletContext.setInitParameter("contextConfigLocation", "com.feritoth.ticketingsystem");
	}

}