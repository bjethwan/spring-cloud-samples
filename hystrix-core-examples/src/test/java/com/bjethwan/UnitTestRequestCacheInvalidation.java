package com.bjethwan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bjethwan.CommandUsingRequestCacheInvalidation.GetterCommand;
import com.bjethwan.CommandUsingRequestCacheInvalidation.SetterCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class UnitTestRequestCacheInvalidation {

    @Test
    public void getGetSetGet() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            assertEquals("ValueBeforeSet_1", new GetterCommand(1).execute());
            GetterCommand commandAgainstCache = new GetterCommand(1);
            assertEquals("ValueBeforeSet_1", commandAgainstCache.execute());
            // confirm it executed against cache the second time
            assertTrue(commandAgainstCache.isResponseFromCache());
            // set the new value
            new SetterCommand(1, "ValueAfterSet_").execute();
            // fetch it again
            GetterCommand commandAfterSet = new GetterCommand(1);
            // the getter should return with the new prefix, not the value from cache
            assertFalse(commandAfterSet.isResponseFromCache());
            assertEquals("ValueAfterSet_1", commandAfterSet.execute());
        } finally {
            context.shutdown();
        }
    }
}
