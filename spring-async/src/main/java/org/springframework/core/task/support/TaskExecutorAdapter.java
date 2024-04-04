package org.springframework.core.task.support;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.springframework.util.concurrent.ListenableFuture;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.spring.web.async.Utils;

@Weave
public abstract class TaskExecutorAdapter {

	public void execute(Runnable task) {
		Runnable wrapper = Utils.getWrappedRunnable(task);
		if(wrapper != null) {
			task = wrapper;
		}
		Weaver.callOriginal();
	}
	
	public Future<?> submit(Runnable task) {
		Runnable wrapper = Utils.getWrappedRunnable(task);
		if(wrapper != null) {
			task = wrapper;
		}
		return Weaver.callOriginal();
	}
	
	public <T> Future<T> submit(Callable<T> task) {
		Callable<T> wrapper = Utils.getWrappedCallable(task);
		if(wrapper != null) {
			task = wrapper;
		}
		return Weaver.callOriginal();
	}
	
	public ListenableFuture<?> submitListenable(Runnable task) {
		Runnable wrapper = Utils.getWrappedRunnable(task);
		if(wrapper != null) {
			task = wrapper;
		}
		return Weaver.callOriginal();
	}
	
	public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
		Callable<T> wrapper = Utils.getWrappedCallable(task);
		if(wrapper != null) {
			task = wrapper;
		}
		return Weaver.callOriginal();
	}
}
