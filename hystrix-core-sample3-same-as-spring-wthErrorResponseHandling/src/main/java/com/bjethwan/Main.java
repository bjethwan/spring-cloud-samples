package com.bjethwan;

public class Main {
	
	
	/**
	 * By default hystrix circuit-breaker waits for 20 failures before opening the circuit.
	 * Hence, in below sample after 20th request the response time will improve to local fallback latency
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<35;i++) {
			long start = System.currentTimeMillis();
			try {
				System.out.println(i+ " "+new BackendService().execute() + "and it took " + (System.currentTimeMillis()-start) + "ms" );
			}catch(Exception e) {
				System.out.println(i+ " caught exception " +e.getCause()+" and it took " + (System.currentTimeMillis()-start) + "ms" );
			}
		}
	}
}
