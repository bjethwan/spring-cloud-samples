package com.bjethwan;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service1 {

	private static int counter=0;
	
	@GetMapping("backend1")
	public List<String> getValues() throws InterruptedException{
		if (counter < 10) {
			counter++;
			return Arrays.asList("value1", "value2", "value3");
			
		}else if(counter>=10 && counter<20) {
			counter++;
			TimeUnit.MILLISECONDS.sleep(400);
			return Arrays.asList("errorCode1");
		}else if(counter>=20 && counter<30) {
			counter++;
			TimeUnit.MILLISECONDS.sleep(450);
			return Arrays.asList("errorCode2");
		}else {
			counter=0;
			return Arrays.asList("value1", "value2", "value3");
		}
			
	}
}
