package org.springframework.web.context.request.async;

import java.util.concurrent.Callable;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.spring.web.async.NRCallableWrapper;

@Weave
public abstract class WebAsyncTask<V> {
	
	
	public Callable<?> getCallable() {
		Callable<?> callable = Weaver.callOriginal();
		if(!(callable instanceof NRCallableWrapper)) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				return new NRCallableWrapper<>(callable, t);
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
		return callable;
	}
	
}
