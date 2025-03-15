package org.kubsu.tuning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kubsu.tuning.domain.entities.ParamAndValue;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigWriteDto {
    Long sysId;
    List<ParamAndValue> paramsAndValues;
    String description;
}
