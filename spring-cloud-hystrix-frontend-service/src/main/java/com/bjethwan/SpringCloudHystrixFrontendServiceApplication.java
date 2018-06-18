package com.bjethwan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


// @EnableHystrix or @EnableCircuitBreaker
// Use @EnableHystrix when you need the Hystrix dashboard
@SpringBootApplication
@EnableCircuitBreaker
public class SpringCloudHystrixFrontendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudHystrixFrontendServiceApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	RestTemplate resttemplate() {
		return new RestTemplate();
	}
}
