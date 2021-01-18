package com.bitacademy.mysite.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitacademy.security.AuthInterceptor;
import com.bitacademy.security.AuthUserHandlerMethodArgumentResolver;
import com.bitacademy.security.LoginInterceptor;
import com.bitacademy.security.LogoutInterceptor;

@Configuration
@PropertySource("classpath:config/webconfig.properties")
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private Environment env;
	
	// 1. Argument Resolvers
	@Bean
	public HandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}

	// 2. Interceptors
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
		registry.addInterceptor(logoutIntercepter()).addPathPatterns(env.getProperty("security.logout-url"));

		registry.addInterceptor(loginIntercepter()).addPathPatterns(env.getProperty("security.login-url"));

		registry.addInterceptor(authIntercepter())
					.addPathPatterns("/**")
					.excludePathPatterns(env.getProperty("security.logout-url"))
					.excludePathPatterns(env.getProperty("security.login-url"))
					.excludePathPatterns(env.getProperty("web.statics") + "/**");
	}
	
	// MVC Resource Mapping(URL Magic Mapping)
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(env.getProperty("resource.mapping"))
			.addResourceLocations("file:" + env.getProperty("resource.locations"));
	}
}
