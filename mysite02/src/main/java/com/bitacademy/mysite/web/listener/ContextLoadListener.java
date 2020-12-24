package com.bitacademy.mysite.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoadListener implements ServletContextListener {

    public ContextLoadListener() {
    	
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
        
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
         System.out.println("Application starts....");
    }	
}
