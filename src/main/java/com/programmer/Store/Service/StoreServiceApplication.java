package com.programmer.Store.Service;

import com.programmer.Store.Service.service.impl.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@SpringBootApplication
public class StoreServiceApplication {
	@Autowired
	public static StoreServiceImpl storeServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(StoreServiceApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(List.of("http://localhost:4200")); // Frontend origin
		corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		corsConfig.setAllowedHeaders(List.of("Content-Type", "Authorization"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsFilter(source);
	}

}
