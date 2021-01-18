package com.bitacademy.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:com/bitacademy/mysite/config/web/properties/fileupload.properties")
public class FileUploadConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Bean
	public MultipartResolver commonMultipartResolver() {
		CommonsMultipartResolver mulitpartResolver = new CommonsMultipartResolver();
		mulitpartResolver.setMaxUploadSize(env.getProperty("fileupload.maxUploadSize", Long.class));
		mulitpartResolver.setMaxInMemorySize(env.getProperty("fileupload.maxInMemorySize", Integer.class));
		mulitpartResolver.setDefaultEncoding(env.getProperty("fileupload.defaultEncoding"));
		
		return mulitpartResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler(env.getProperty("fileupload.resourceMapping"))
			.addResourceLocations("file:" + env.getProperty("fileupload.resourceLocations"));
	}
	
	
}
