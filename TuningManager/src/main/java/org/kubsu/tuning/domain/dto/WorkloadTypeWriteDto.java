package org.kubsu.tuning.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkloadTypeWriteDto {
    private String name;
    private Long workloadProfileId;
    private Double leftBoundary;
    private Double rightBoundary;
}
