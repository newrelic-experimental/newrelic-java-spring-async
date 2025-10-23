package com.newrelic.instrumentation.spring.async;

import java.util.concurrent.Callable;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRCallableWrapper<V> implements Callable<V> {
	
	private Callable<V> delegate = null;
	private Token token = null;
	private static boolean isTransformed = false;
	
	public NRCallableWrapper(Callable<V> d, Token t) {
		delegate = d;
		token = t;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(async = true)
	public V call() throws Exception {
		if(token != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","SpringAsync","Callable");
			token.linkAndExpire();
			token = null;
		}
		return delegate.call();
	}

}
