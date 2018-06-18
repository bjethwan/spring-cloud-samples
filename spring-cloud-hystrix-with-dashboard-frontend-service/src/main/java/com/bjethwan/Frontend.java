package com.bjethwan;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class Frontend {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/frontend")
	@HystrixCommand(fallbackMethod="fallbackGetValues", commandProperties={
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
	})
	public List<String> getValues(@RequestParam(name="wait") long wait) throws InterruptedException{

		if(wait>0) {
			TimeUnit.MILLISECONDS.sleep(wait);
		}
		return restTemplate.getForObject("http://localhost:8181/backend1", List.class);
	}

	public List<String> fallbackGetValues(long wait){
		return Arrays.asList("defaultValue1", "defaultValue2", "defaultValue3", "defaultValue4");
	}
}
