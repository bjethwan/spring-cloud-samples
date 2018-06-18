package com.bjethwan;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service1 {

	@GetMapping("backend1")
	public List<String> getValues(){
		return Arrays.asList("value1", "value2", "value3");
	}
}
