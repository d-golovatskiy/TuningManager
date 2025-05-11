package org.kubsu.tuning.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kubsu.tuning.domain.entities.InfluxTagValue;

@Data
@NoArgsConstructor
public class InfluxTagValueDto {
    private String tagName;
    private String value;

    public InfluxTagValueDto(InfluxTagValue influxTagValues) {
        this.tagName = influxTagValues.getInfluxTag().getName();
        this.value = influxTagValues.getValue();
    }
}
