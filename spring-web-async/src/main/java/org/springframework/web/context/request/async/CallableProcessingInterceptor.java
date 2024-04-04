package org.springframework.web.context.request.async;

import java.util.concurrent.Callable;

import org.springframework.web.context.request.NativeWebRequest;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.spring.web.async.NRCallableWrapper;

@Weave(type = MatchType.Interface)
public abstract class CallableProcessingInterceptor {

	@Trace(async = true)
	public <T> void afterCompletion(NativeWebRequest request, Callable<T> task)  {
		if(task instanceof NRCallableWrapper) {
			NRCallableWrapper<T> wrapper = (NRCallableWrapper<T>)task;
			wrapper.linkToken();
			wrapper.expireToken();
		}


		Weaver.callOriginal();
	}

	@Trace(async = true)
	public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task)  {
		NewRelic.noticeError("Received WebAsync Timeout");
		if(task instanceof NRCallableWrapper) {
			((NRCallableWrapper<T>)task).linkToken();
		}
		
		return Weaver.callOriginal();
	}
	
	@Trace(async = true)
	public <T> Object handleError(NativeWebRequest request, Callable<T> task, Throwable t) {
		NewRelic.noticeError(t);
		if(task instanceof NRCallableWrapper) {
			((NRCallableWrapper<T>)task).linkToken();
		}
		
		return Weaver.callOriginal();
	}
	
		

}
