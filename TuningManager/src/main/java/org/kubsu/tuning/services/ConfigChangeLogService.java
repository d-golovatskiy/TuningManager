package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.dto.ConfigWriteDto;
import org.kubsu.tuning.domain.entities.Config;
import org.kubsu.tuning.domain.entities.ConfigChangeLog;
import org.kubsu.tuning.domain.entities.ParamAndValue;
import org.kubsu.tuning.repositories.ConfigChangeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigChangeLogService {
    @Autowired
    private ConfigChangeLogRepository configChangeLogRepository;
    @Autowired
    private ConfigService configService;

    public ConfigChangeLog save(ConfigChangeLog configChangeLog) {
        return configChangeLogRepository.save(configChangeLog);
    }

    public List<ConfigChangeLog> saveAll(List<ConfigChangeLog> configChangeLogs) {
        return configChangeLogRepository.saveAll(configChangeLogs);
    }

    public Optional<ConfigChangeLog> findById(Long id) {
        return configChangeLogRepository.findById(id);
    }

    public List<ConfigChangeLog> findAll() {
        return configChangeLogRepository.findAll();
    }

    public List<ConfigChangeLog> findAllByParamAndValue(ParamAndValue paramAndValue) {
        return configChangeLogRepository.findByParam(paramAndValue);
    }

    public List<ConfigChangeLog> createAndSaveByConfigWriteDto(Long configID, ConfigWriteDto configWriteDto) {
        Config config = configService.findById(configID);
        List<ConfigChangeLog> configChangeLogsToSave = new ArrayList<>();
        configWriteDto.getParamsAndValues().forEach(param -> {
            ParamAndValue paramAndValue = config.getParams().stream().filter(p -> p.getName().equals(param.getName()))
                    .findFirst().orElse(null);
            if (paramAndValue != null) {
                if (!paramAndValue.getValue().equals(param.getValue())
                        && paramAndValue.getConfigChangeLogs().stream()
                        .filter(e -> e.getParam().getName().equals(param.getName()))
                        .noneMatch(e -> e.getNewValue().equals(param.getValue()))) {
                    ConfigChangeLog configChangeLog = new ConfigChangeLog();
                    configChangeLog.setParam(paramAndValue);
                    configChangeLog.setNewValue(param.getValue());
                    configChangeLog.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));
                    configChangeLogsToSave.add(configChangeLog);
                }
            } else {
                param.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
                param.setConfig(config);
                config.getParams().add(param);
                configService.save(config);
            }
        });
        return saveAll(configChangeLogsToSave);
    }

    public void deleteById(Long id) {
        configChangeLogRepository.deleteById(id);
    }
}
