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

public class BackupOfBackendService extends HystrixCommand<List<String>>{
	private static Client client = ClientBuilder.newClient();
	protected BackupOfBackendService() {
//		super(Setter
//				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DownstreamBackendServices")));
		
		super(Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DownstreamBackendServices"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("BackendService2"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("BackendService2ThreadPool"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withExecutionTimeoutInMilliseconds(500))
				);
	}

	@Override
	protected List<String> run() throws Exception {
		String threadName = Thread.currentThread().getName();
		List<String> response = client
				.target("http://localhost:8282")
				.path("backend2")
				.request(MediaType.APPLICATION_JSON)
				.get(List.class);
		response.add(threadName);
		return response;
	}
	
	@Override
	protected List<String> getFallback() {
		return Arrays.asList("defaultValue1", "defaultValue2", "defaultValue3", "defaultValue4");
	}

}
