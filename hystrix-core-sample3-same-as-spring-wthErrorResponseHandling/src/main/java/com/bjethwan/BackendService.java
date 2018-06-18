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

		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DownstreamBackendServices"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("BackendService1"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("BackendService1ThreadPool"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withCircuitBreakerRequestVolumeThreshold(5))
				);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<String> run() throws Exception {
		List<String> response = client
				.target("http://localhost:8181")
				.path("backend1")
				.request(MediaType.APPLICATION_JSON)
				.get(List.class);

		if(response.contains("errorCode1")) {
			throw new Failure1Exception();
		}else if(response.contains("errorCode2")) {
			throw new Failure2Exception();
		}
		return response;
	}

	@Override
	protected List<String> getFallback() {
		System.out.println("-----------------------------"+getExecutionException());
		System.out.println("-----------------------------"+getExecutionException().getCause());
		System.out.println("-----------------------------isCircuitBreakerOpen():"+isCircuitBreakerOpen());
		System.out.println("-----------------------------isResponseShortCircuited():"+isResponseShortCircuited());
		
		if(isFailedExecution()) {
			if(getFailedExecutionException() instanceof Failure1Exception) {
				return Arrays.asList("errorCode1");
			}
			else if(getFailedExecutionException() instanceof Failure2Exception) {
				return Arrays.asList("errorCode2");
			}
		}
		
		return Arrays.asList("defaultValue1", "defaultValue2", "defaultValue3", "defaultValue4");
	}

}
