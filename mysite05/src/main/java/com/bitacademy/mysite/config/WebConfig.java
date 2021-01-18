package com.bitacademy.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.bitacademy.config.web.MessageSourceConfig;
import com.bitacademy.config.web.MvcConfig;
import com.bitacademy.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.bitacademy.mysite.controller", "com.bitacademy.mysite.exception"})
@Import({MvcConfig.class, SecurityConfig.class, MessageSourceConfig.class})
public class WebConfig {

}
