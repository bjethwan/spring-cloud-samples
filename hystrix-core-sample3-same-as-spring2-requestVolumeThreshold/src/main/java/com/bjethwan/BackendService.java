package com.bjethwan;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class BackendService extends HystrixCommand<List<String>>{
	private static Client client = ClientBuilder.newClient();
	protected BackendService() {
//		super(Setter
//				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DownstreamBackendServices")));
		
		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DownstreamBackendServices"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("BackendService1"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("BackendService1ThreadPool"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withExecutionTimeoutInMilliseconds(700)
						.withCircuitBreakerRequestVolumeThreshold(5)
						.withCircuitBreakerSleepWindowInMilliseconds(1000))
				);
	}

	@Override
	protected List<String> run() throws Exception {
		List<String> response = client
				.target("http://localhost:8181")
				.path("backend1")
				.request(MediaType.APPLICATION_JSON)
				.get(List.class);
		return response;
	}
	
	@Override
	protected List<String> getFallback() {
		return Arrays.asList("defaultValue1", "defaultValue2", "defaultValue3", "defaultValue4");
	}

}
