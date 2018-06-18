package com.bjethwan;

import static org.junit.Assert.*;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;


public class UnitTest {

	@Test
	public void testWithoutCacheHits() {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			assertTrue(new CommandUsingRequestCache(2).execute());
			assertFalse(new CommandUsingRequestCache(1).execute());
			assertTrue(new CommandUsingRequestCache(0).execute());
			assertTrue(new CommandUsingRequestCache(58672).execute());
		} finally {
			context.shutdown();
		}
	}

	@Test
	public void testWithCacheHits() {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		try {
			CommandUsingRequestCache command2a = new CommandUsingRequestCache(2);
			CommandUsingRequestCache command2b = new CommandUsingRequestCache(2);

			assertTrue(command2a.execute());
			// this is the first time we've executed this command with the value of "2" so it should not be from cache
			assertFalse(command2a.isResponseFromCache());

			assertTrue(command2b.execute());
			// this is the second time we've executed this command with the same value so it should return from cache
			assertTrue(command2b.isResponseFromCache());
		} finally {
			context.shutdown();
		}

		// start a new request context
		context = HystrixRequestContext.initializeContext();
		try {
			CommandUsingRequestCache command3b = new CommandUsingRequestCache(2);
			assertTrue(command3b.execute());
			// this is a new request context so this should not come from cache
			assertFalse(command3b.isResponseFromCache());
		} finally {
			context.shutdown();
		}
	}
}
