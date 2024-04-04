package com.newrelic.instrumentation.spring.web.async;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRRunnableWrapper implements Runnable {
	
	private static boolean isTransformed = false;
	private Runnable delegate = null;
	private Token token = null;
	
	public NRRunnableWrapper(Runnable r, Token t) {
		delegate = r;
		token = t;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}
	
	@Override
	@Trace(async = true)
	public void run() {
		if(token != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","SpringAsync","Runnable");
			token.linkAndExpire();
			token = null;
		}
		delegate.run();
	}

}
