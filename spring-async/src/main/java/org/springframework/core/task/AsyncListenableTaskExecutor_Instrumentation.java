package org.springframework.core.task;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.spring.async.Utils;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;

@Weave(originalName = "org.springframework.core.task.AsyncListenableTaskExecutor", type = MatchType.Interface)
public class AsyncListenableTaskExecutor_Instrumentation {

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
