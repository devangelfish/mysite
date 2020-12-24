package com.bitacademy.mysite.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

    public ContextLoadListener() {
    	
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
        
    }

    public void contextInitialized(ServletContextEvent servletContextEvent)  { 
    	ServletContext context = servletContextEvent.getServletContext();
    	String contextConfigLocation = context.getInitParameter("contextConfigLocation");
    	
        System.out.println("Application starts...." + contextConfigLocation);
    }	
}
