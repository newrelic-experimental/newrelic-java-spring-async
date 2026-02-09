package org.springframework.core.task;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(originalName = "org.springframework.core.task.TaskCallback", type = MatchType.Interface)
public class TaskCallback_Instrumentation<V, E extends Exception> {

    @Trace
    public V call() throws E {
        return Weaver.callOriginal();
    }
}
