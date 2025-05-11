package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.InfluxTag;
import org.kubsu.tuning.domain.entities.InfluxTagValue;
import org.kubsu.tuning.repositories.InfluxTagValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfluxTagValueService {
    @Autowired
    private InfluxTagValuesRepository influxTagValuesRepository;

    public InfluxTagValue save(InfluxTagValue influxTagValue) {
        return influxTagValuesRepository.save(influxTagValue);
    }

    public InfluxTagValue findById(Long id) {
        return influxTagValuesRepository.findById(id).orElse(null);
    }

    public List<InfluxTagValue> findAll() {
        return influxTagValuesRepository.findAll();
    }

    public List<InfluxTagValue> findAllByIds(List<Long> ids) {
        return influxTagValuesRepository.findAllById(ids);
    }
}
