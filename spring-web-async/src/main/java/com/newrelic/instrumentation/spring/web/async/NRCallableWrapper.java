package com.newrelic.instrumentation.spring.web.async;

import java.util.concurrent.Callable;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRCallableWrapper<V> implements Callable<V> {
	
	private static boolean isTransformed = false;
	private Callable<V> delegate = null;
	private Token token = null;
	
	public NRCallableWrapper(Callable<V> d, Token t) {
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
		delegate = d;
		token = t;
	}

	@Override
	@Trace(async = true)
	public V call() throws Exception {
		if(token != null) {
			token.link();
		}
		return delegate.call();
	}

	public void linkToken() {
		if(token != null) {
			token.link();
		}
	}
	
	public void expireToken() {
		if(token != null) {
			token.expire();
			token = null;
		}
	}
}
