package com.newrelic.instrumentation.spring.web.async;

import java.util.concurrent.Callable;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class Utils {

	public static Runnable getWrappedRunnable(Runnable r) {
		
		if(r instanceof NRRunnableWrapper) return null;
		
		String packageName = r.getClass().getPackage().getName();
		if(packageName.startsWith("com.newrelic")) return null;
		
		Token token = NewRelic.getAgent().getTransaction().getToken();
		if(token != null && token.isActive()) {
			return new NRRunnableWrapper(r, token);
		} else if(token != null) {
			token.expire();
			token = null;
		}
		return null;
	}
	
	public static <V> Callable<V> getWrappedCallable(Callable<V> c) {
		
		if(c instanceof NRCallableWrapper) return null;
		
		String packageName = c.getClass().getPackage().getName();
		if(packageName.startsWith("com.newrelic")) return null;
		
		Token token = NewRelic.getAgent().getTransaction().getToken();
		if(token != null && token.isActive()) {
			return new NRCallableWrapper<V>(c, token);
		} else if(token != null) {
			token.expire();
			token = null;
		}
		return null;
	}
	
}
