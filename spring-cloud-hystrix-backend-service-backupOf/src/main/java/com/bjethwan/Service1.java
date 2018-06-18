package com.bjethwan;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service1 {

	@GetMapping("backend2")
	public List<String> getValues(){
		return Arrays.asList("backupOf-Value1", "backupOf-value2", "backupOf-value3");
	}
}
