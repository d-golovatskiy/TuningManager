package org.kubsu.tuning.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kubsu.tuning.domain.entities.Config;
import org.kubsu.tuning.domain.entities.Measurements;
import org.kubsu.tuning.domain.entities.WorkloadProfile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class TaskToConfigure {
    private Long requestToConfigureId;
    private List<String> filenames = new ArrayList<>();
    private Timestamp configurePeriodStartDate;
    private Timestamp configurePeriodEndDate;
    private Measurements measurements;
    private Config config;
    private WorkloadProfile workloadProfile;
}
