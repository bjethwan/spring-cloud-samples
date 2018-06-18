package com.bjethwan;

import java.util.concurrent.TimeUnit;

public class Main {
	
	
	/**
	 * By default hystrix circuit-breaker waits for 20 failures before opening the circuit.
	 * Hence, in below sample after 20th request the response time will improve to local fallback latency
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		new BackendService().execute();
		for(int i=0;i<25;i++) {
			long start = System.currentTimeMillis();
			System.out.println(new BackendService().execute() + "and it took " + (System.currentTimeMillis()-start) + "ms" );
			if(i==23) TimeUnit.SECONDS.sleep(6);
		}
	}
}
