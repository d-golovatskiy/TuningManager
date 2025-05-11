package org.kubsu.tuning.domain.dto;

import lombok.Data;
import org.kubsu.tuning.domain.TaskResult;

@Data
public class TaskToConfigureResultDto {
    private Long taskId;
    private TaskResult taskResult;
}
