package com.ispan.warashibe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ispan.warashibe.util.JsonWebTokenInterceptor;

@Configuration
public class springBootJavaConfig implements WebMvcConfigurer {

	@Autowired
	private JsonWebTokenInterceptor jsonWebTokenInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(jsonWebTokenInterceptor)
				.addPathPatterns("/ajax/members/**");
//				.excludePathPatterns("/ajax/pages/detail/**");
	}

}
