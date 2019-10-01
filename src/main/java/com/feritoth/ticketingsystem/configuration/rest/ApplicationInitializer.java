package com.feritoth.ticketingsystem.configuration.rest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/* This annotation together with its parameter will ensure that the current initializer will be executed before the setup of the Jersey context */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		/* Create the annotation configuration context and use it for configuring the ServletContext passed in as parameter */
		AnnotationConfigWebApplicationContext configContext = new AnnotationConfigWebApplicationContext();
		/* Add the context listener for enabling the load procedure */
		servletContext.addListener(new ContextLoaderListener(configContext));
		/* Add the setup location - the package to be scanned for Jersey references (this will prevent Jersey from searching for the
		   Spring configuration file applicationContext.xml and throw a FileNotFoundException) */
		servletContext.setInitParameter("contextConfigLocation", "com.feritoth.ticketingsystem");
	}

}