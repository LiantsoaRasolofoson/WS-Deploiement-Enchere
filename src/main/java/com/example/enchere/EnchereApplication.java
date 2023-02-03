package com.example.enchere;

import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class EnchereApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnchereApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
						.allowedHeaders("*");
			}
		};
	}

	// @Bean
	// public FilterRegistrationBean corsFilter() {
	// 	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	// 	CorsConfiguration config = new CorsConfiguration();
	// 	config.setAllowCredentials(true);
	// 	config.addAllowedOrigin("*");
	// 	config.addAllowedHeader("*");
	// 	config.addAllowedMethod("OPTIONS");
	// 	config.addAllowedMethod("HEAD");
	// 	config.addAllowedMethod("GET");
	// 	config.addAllowedMethod("PUT");
	// 	config.addAllowedMethod("POST");
	// 	config.addAllowedMethod("DELETE");
	// 	config.addAllowedMethod("PATCH");
	// 	source.registerCorsConfiguration("/**", config);
	// 	// return new CorsFilter(source);
	// 	final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	// 	bean.setOrder(0);
	// 	return bean;
	// }
}
