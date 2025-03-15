package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.ConfigureRequestMeasurements;
import org.kubsu.tuning.repositories.ConfigureRequestMeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfigureRequestMeasurementsService {
    @Autowired
    private ConfigureRequestMeasurementsRepository configureRequestMeasurementsRepository;

    public ConfigureRequestMeasurements save(ConfigureRequestMeasurements configureRequestMeasurement) {
        return configureRequestMeasurementsRepository.save(configureRequestMeasurement);
    }

    public List<ConfigureRequestMeasurements> saveAll(List<ConfigureRequestMeasurements> configureRequestMeasurements) {
        return configureRequestMeasurementsRepository.saveAll(configureRequestMeasurements);
    }

    public List<ConfigureRequestMeasurements> findAll() {
        return configureRequestMeasurementsRepository.findAll();
    }

    public Optional<ConfigureRequestMeasurements> findById(Long id) {
        return configureRequestMeasurementsRepository.findById(id);
    }

    public void delete(ConfigureRequestMeasurements configureRequestMeasurement) {
        configureRequestMeasurementsRepository.delete(configureRequestMeasurement);
    }
}
