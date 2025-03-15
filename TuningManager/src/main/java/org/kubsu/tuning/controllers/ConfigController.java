package org.kubsu.tuning.controllers;

import org.kubsu.tuning.domain.dto.ConfigWriteDto;
import org.kubsu.tuning.domain.entities.Config;
import org.kubsu.tuning.domain.entities.ConfigChangeLog;
import org.kubsu.tuning.services.ConfigChangeLogService;
import org.kubsu.tuning.services.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/configs")
public class ConfigController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private ConfigChangeLogService changeLogService;
    @Autowired
    private ConfigChangeLogService configChangeLogService;

    @GetMapping("")
    public List<Config> getAllConfigs() {
        return configService.findAll();
    }

    @GetMapping("/{id}")
    public Config getAConfigById(@PathVariable Long id) {
        return configService.findById(id);
    }

    @PostMapping("")
    public Config saveConfig(@RequestBody ConfigWriteDto configWriteDto) {
        return configService.createAndSaveByConfigWriteDto(configWriteDto);
    }

    @PatchMapping("/{id}")
    public Config updateConfig(@PathVariable Long id, @RequestBody ConfigWriteDto configWriteDto) {
        return configService.updateByConfigWriteDto(id, configWriteDto);
    }

    @PutMapping("/{id}/change-logs")
    public List<ConfigChangeLog> putChangeLogs(@PathVariable("id") Long configId, @RequestBody ConfigWriteDto configWriteDto) {
        return configChangeLogService.createAndSaveByConfigWriteDto(configId, configWriteDto);
    }

    @DeleteMapping("/{id}")
    public void deleteConfig(@PathVariable Long id) {
        configService.deleteById(id);
    }
}
