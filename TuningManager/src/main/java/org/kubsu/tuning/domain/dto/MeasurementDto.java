package org.kubsu.tuning.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kubsu.tuning.domain.entities.Measurements;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MeasurementDto {
    private Long id;
    private String name;
    private String fieldName;
    private List<InfluxTagValueDto> influxTagValues = new ArrayList<>();
    private String influxBucketName;

    public MeasurementDto(Measurements measurements) {
        this.id = measurements.getId();
        this.name = measurements.getName();
        this.fieldName = measurements.getFieldName();
        if (!measurements.getMeasAndInfluxTagValues().isEmpty()) {
            measurements.getMeasAndInfluxTagValues().forEach(e -> {
                this.influxTagValues.add(new InfluxTagValueDto(e.getInfluxTagValue()));
            });
            if (!influxTagValues.isEmpty()) {
                this.influxBucketName = measurements.getMeasAndInfluxTagValues().get(0).getInfluxTagValue().getInfluxTag().getBucket().getName();
            }
        }
    }
}
