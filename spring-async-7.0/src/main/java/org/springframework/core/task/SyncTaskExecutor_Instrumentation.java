package org.springframework.core.task;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "org.springframework.core.task.SyncTaskExecutor")
public class SyncTaskExecutor_Instrumentation {

    @Trace
    public <V, E extends Exception> V execute(TaskCallback<V, E> task) {
        return Weaver.callOriginal();
    }
}
