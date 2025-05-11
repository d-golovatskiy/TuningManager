package org.kubsu.tuning.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WorkloadProfileWriteDto {
    private Long sysId;
    private String name;
    private String description;
    private List<WorkloadTypeWriteDto> workloadTypes = new ArrayList<>();
}
