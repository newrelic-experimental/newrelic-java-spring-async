package org.springframework.core.task;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.spring.web.async.Utils;

@Weave
public abstract class SimpleAsyncTaskExecutor {

	
	protected void doExecute(Runnable task) {
		Runnable wrapper = Utils.getWrappedRunnable(task);
		if(wrapper != null) {
			task = wrapper;
		}
		Weaver.callOriginal();
	}
}
