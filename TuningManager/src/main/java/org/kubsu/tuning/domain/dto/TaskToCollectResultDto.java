package org.kubsu.tuning.domain.dto;

import org.kubsu.tuning.domain.TaskResult;

public class TaskToCollectResultDto {
    private Long taskId;
    private TaskResult taskToCollectResult;
    public Long getTaskId() {
        return taskId;
    }
    public TaskResult getTaskToCollectResult() {
        return taskToCollectResult;
    }
}
