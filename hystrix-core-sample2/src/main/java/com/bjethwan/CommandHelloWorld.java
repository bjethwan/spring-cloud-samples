package com.bjethwan;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandHelloWorld extends HystrixCommand<String> 
{
	private final String name;

	public CommandHelloWorld(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected String run() {
		double random = Math.random();
		if(random>0.5)
			return "Hello " + name + "!";
		else
			throw new RuntimeException("this command always fails");
	}

	@Override
	protected String getFallback() {
		return "Hello Failure " + name + "!";
	}
}

