package org.springframework.core.task;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.spring.async.Utils;

@Weave(originalName = "org.springframework.core.task.TaskExecutor", type = MatchType.Interface)
public class TaskExecutor_Instrumentation {

    public void execute(Runnable task) {
        Runnable wrapper = Utils.getWrappedRunnable(task);
        if(wrapper != null) {
            task = wrapper;
        }
        Weaver.callOriginal();
    }
}
