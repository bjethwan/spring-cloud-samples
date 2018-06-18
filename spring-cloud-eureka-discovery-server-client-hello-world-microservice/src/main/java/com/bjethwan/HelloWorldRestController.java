package com.bjethwan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloWorldRestController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("hello")
	public String sayHello() {
		
		String greetingMessage = restTemplate.getForObject("http://GREETINGMESSAGE/msg", String.class);
		return "Hello World. <br> "+ greetingMessage;
	}

}
