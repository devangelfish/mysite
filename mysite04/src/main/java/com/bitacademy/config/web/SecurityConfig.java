package com.bitacademy.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bitacademy.security.AuthInterceptor;
import com.bitacademy.security.AuthUserHandlerMethodArgumentResolver;
import com.bitacademy.security.LoginInterceptor;
import com.bitacademy.security.LogoutInterceptor;

@Configuration
@EnableWebMvc
public class SecurityConfig extends WebMvcConfigurerAdapter {
	//1. Argument Resolvers
	
	@Bean
	public HandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}
	
	//2. Interceptors
	@Bean
	public HandlerInterceptor logoutIntercepter() {
		return new LogoutInterceptor();
		
	}
	
	@Bean
	public HandlerInterceptor loginIntercepter() {
		return new LoginInterceptor();
		
	}
	
	@Bean
	public HandlerInterceptor authIntercepter() {
		return new AuthInterceptor();
		
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(logoutIntercepter())
			.addPathPatterns("/user/logout");
		
		registry
			.addInterceptor(loginIntercepter())
			.addPathPatterns("/user/auth");
		
		registry
			.addInterceptor(authIntercepter())
			.addPathPatterns("/**")
			.excludePathPatterns("/user/logout")
			.excludePathPatterns("/user/auth")
			.excludePathPatterns("/assets/**");
	}
	
	
}
