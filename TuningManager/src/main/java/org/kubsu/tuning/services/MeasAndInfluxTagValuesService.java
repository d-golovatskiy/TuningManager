package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.MeasAndInfluxTagValues;
import org.kubsu.tuning.repositories.MeasAndInfluxTagValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasAndInfluxTagValuesService {
    @Autowired
    private MeasAndInfluxTagValuesRepository measAndInfluxTagValuesRepository;

    public MeasAndInfluxTagValues save(MeasAndInfluxTagValues measAndInfluxTagValues) {
        return measAndInfluxTagValuesRepository.save(measAndInfluxTagValues);
    }

    public MeasAndInfluxTagValues findById(Long id) {
        return measAndInfluxTagValuesRepository.findById(id).orElse(null);
    }

    public List<MeasAndInfluxTagValues> findAll() {
        return measAndInfluxTagValuesRepository.findAll();
    }

    public List<MeasAndInfluxTagValues> findAllByMeasIdAndTagValueIds(Long measId, List<Long> tagValueIds) {
        return measAndInfluxTagValuesRepository.findAllByMeasurementsIdAndInfluxTagValueIdIn(measId, tagValueIds);
    }

    public void deleteAllByIds(List<Long> ids) {
        measAndInfluxTagValuesRepository.deleteAllById(ids);
    }
}
