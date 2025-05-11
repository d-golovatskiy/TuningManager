package org.kubsu.tuning.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kubsu.tuning.domain.entities.SysMeas;
import org.kubsu.tuning.domain.entities.TaskToCollect;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TaskToCollectDto {
    private Long id;
    private SysMeasDto sysMeas;
    private SysMeasDto workloadSysMeas;
    private Timestamp dateStart;
    private Timestamp dateEnd;
    private boolean useAffectScheme;

    public TaskToCollectDto(TaskToCollect taskToCollect, SysMeas workloadSysMeas) {
        this.id = taskToCollect.getId();
        this.dateStart = taskToCollect.getDateStart();
        this.dateEnd = taskToCollect.getDateEnd();
        this.sysMeas = new SysMeasDto(taskToCollect.getSysMeas());
        this.workloadSysMeas = new SysMeasDto(workloadSysMeas);
        this.useAffectScheme = taskToCollect.isUseAffectsScheme();
    }
}
