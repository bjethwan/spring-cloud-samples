package com.bjethwan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class SpringCloudEurekaDiscoveryServerClientGreetingMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudEurekaDiscoveryServerClientGreetingMicroserviceApplication.class, args);
	}
	
	@GetMapping("msg")
	public String whatsTheMessage() {
		return "Hardcoded in Spring Cloud!!!";
	}
}
