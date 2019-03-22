package com.example.demo.conf;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by whydda on 2017-07-17.
 */

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		final int cachePeriod = 30 * 24 * 60 * 60;// 1 month
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(cachePeriod);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(cachePeriod);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new DefaultParamsArgumentResolver());
	}

//	@Bean
//	public Filter characterEncodingFilter() {
//		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//		characterEncodingFilter.setEncoding("UTF-8");
//		characterEncodingFilter.setForceEncoding(true);
//		return characterEncodingFilter;
//	}

//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//	InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//	resolver.setPrefix("/WEB-INF/jsp/");	
//	resolver.setSuffix(".jsp");
//	resolver.setViewClass(JstlView.class);
//	registry.viewResolver(resolver);
//}

//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//        .allowedMethods("GET", "POST", "OPTIONS");
//                .allowedOrigins("http://domain2.com")
//                .allowedHeaders("header1", "header2", "header3")
//                .exposedHeaders("header1", "header2")
//                .allowCredentials(false).maxAge(3600);
//    }

}
