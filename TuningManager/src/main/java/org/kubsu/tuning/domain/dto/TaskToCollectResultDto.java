package org.kubsu.tuning.domain.dto;

import org.kubsu.tuning.domain.TaskToCollectResult;

public class TaskToCollectResultDto {
    private Long taskId;
    private TaskToCollectResult taskToCollectResult;

    public Long getTaskId() {
        return taskId;
    }

    public TaskToCollectResult getTaskToCollectResult() {
        return taskToCollectResult;
    }
}
