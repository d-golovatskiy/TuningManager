package org.kubsu.tuning.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
public class RequestToConfigureWriteDto {
    private Long sysId;
    private Timestamp configurePeriodStartDate;
    private Timestamp configurePeriodEndDate;
    private List<Long> measurementIds;
    private String description;
    private String status;
}
