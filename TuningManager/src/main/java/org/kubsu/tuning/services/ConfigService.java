package org.kubsu.tuning.services;

import jakarta.transaction.Transactional;
import org.kubsu.tuning.domain.dto.ConfigWriteDto;
import org.kubsu.tuning.domain.entities.Config;
import org.kubsu.tuning.domain.entities.ParamAndValue;
import org.kubsu.tuning.domain.entities.Sys;
import org.kubsu.tuning.repositories.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfigService {
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private SysService sysService;
    @Autowired
    ParamAndValueService paramAndValueService;

    public List<Config> findAll() {
        return configRepository.findAll();
    }

    public Config findById(Long id) {
        return configRepository.findById(id).orElse(null);
    }

    public Config save(Config config) {
        return configRepository.save(config);
    }

    public void deleteById(Long id) {
        configRepository.deleteById(id);
    }

    public Config createAndSaveByConfigWriteDto(ConfigWriteDto configWriteDto) {
        Config config = new Config();
        config.setDescription(configWriteDto.getDescription());
        Optional<Sys> sys = sysService.findById(configWriteDto.getSysId());
        if (sys.isEmpty()) {
            throw new IllegalArgumentException("No such sys");
        }
        config.setSys(sys.get());
        config.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
        configWriteDto.getParamsAndValues().forEach(param -> {
            param.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            param.setConfig(config);
            config.getParams().add(param);
        });
        return save(config);
    }

    public Config updateByConfigWriteDto(Long id, ConfigWriteDto configWriteDto) {
        Config config = findById(id);
        if (config.getParams().stream().anyMatch(e -> !e.getConfigChangeLogs().isEmpty())) {
            throw new IllegalArgumentException("Cannot to update config, because change log is not empty");
        }
        config.setDescription(configWriteDto.getDescription());
        if (configWriteDto.getSysId() != null) {
            config.setSys(sysService.findById(configWriteDto.getSysId()).orElseThrow());
        }
        List<ParamAndValue> paramAndValuesToSave = new ArrayList<>();
        List<Integer> paramAndValueIdsToDelete = new ArrayList<>();
        configWriteDto.getParamsAndValues().forEach(param -> {
            ParamAndValue paramAndValue = config.getParams().stream()
                    .filter(p -> p.getName().equals(param.getName()))
                    .findFirst().orElse(null);
            if (paramAndValue == null) {
                param.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
                param.setConfig(config);
            } else {
                paramAndValue.setValue(param.getValue());
            }
            paramAndValuesToSave.add(paramAndValue != null ? paramAndValue : param);
        });
        config.getParams().forEach(param -> {
            if (!paramAndValuesToSave.contains(param)) {
                paramAndValueIdsToDelete.add(param.getId());
            }
        });
        config.setParams(paramAndValuesToSave);
        paramAndValueIdsToDelete.forEach(paramId -> {
            paramAndValueService.deleteById(paramId);
        });
        return save(config);
    }

    public Config findBySys(Sys sys) {
        return configRepository.findBySys(sys);
    }
}
