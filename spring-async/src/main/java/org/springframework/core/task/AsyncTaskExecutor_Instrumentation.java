package org.springframework.core.task;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.spring.async.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Weave(originalName = "org.springframework.core.task.AsyncTaskExecutor", type = MatchType.Interface)
public class AsyncTaskExecutor_Instrumentation {

    public void execute(Runnable task, long startTimeout) {
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

    public <V> Future<V> submit(Callable<V> task) {
        Callable<V> wrapper = Utils.getWrappedCallable(task);
        if(wrapper != null) {
            task = wrapper;
        }
        return Weaver.callOriginal();
    }
}
