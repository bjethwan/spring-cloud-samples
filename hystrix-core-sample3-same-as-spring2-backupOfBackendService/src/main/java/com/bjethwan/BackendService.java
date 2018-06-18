package com.bjethwan;

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
						.withExecutionTimeoutInMilliseconds(500))
				);
	}

	@Override
	protected List<String> run() throws Exception {
		String threadName = Thread.currentThread().getName();
		List<String> response = client
				.target("http://localhost:8181")
				.path("backend1")
				.request(MediaType.APPLICATION_JSON)
				.get(List.class);
		response.add(threadName);
		return response;
	}
	
	@Override
	protected List<String> getFallback() {
		return new BackupOfBackendService().execute();	
	}

}
