package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.InfluxBucket;
import org.kubsu.tuning.domain.entities.InfluxTag;
import org.kubsu.tuning.domain.entities.InfluxTagValue;
import org.kubsu.tuning.domain.entities.MeasAndInfluxTagValues;
import org.kubsu.tuning.domain.entities.Measurements;
import org.kubsu.tuning.repositories.MeasAndInfluxTagValuesRepository;
import org.kubsu.tuning.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementService {
    @Autowired
    private MeasurementsRepository measurementsRepository;

    @Autowired
    private MeasAndInfluxTagValuesService measAndInfluxTagValuesService;

    @Autowired
    private InfluxTagValueService influxTagValueService;

    public List<Measurements> findById(Long id){
        if (id == null){
            return measurementsRepository.findAll();
        }
        return List.of(measurementsRepository.findById(id).get());
    }

    public ResponseEntity<HttpStatus> save(Measurements measurements){
        if (measurements.getMeasAndInfluxTagValues().stream()
                .map(e -> e.getInfluxTagValue().getInfluxTag().getBucket())
                .distinct().count() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        measurementsRepository.save(measurements);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> update(Measurements measurements){
        measurementsRepository.save(measurements);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id){
        measurementsRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public List<MeasAndInfluxTagValues> setTagValuesByIds(Long measId, List<Long> tagValueIds) {
        if (measId == null) {
            throw new IllegalArgumentException("measId must not be null");
        }
        Measurements measurements = findById(measId).get(0);
        List<MeasAndInfluxTagValues> measAndInfluxTagValuesExist = measAndInfluxTagValuesService.findAllByMeasIdAndTagValueIds(measId, tagValueIds);
        measAndInfluxTagValuesService.deleteAllByIds(measAndInfluxTagValuesExist.stream().map(MeasAndInfluxTagValues::getId).collect(Collectors.toList()));
        measurements.getMeasAndInfluxTagValues().clear();
        List<InfluxTagValue> influxTagValues =  influxTagValueService.findAllByIds(tagValueIds);
        if (influxTagValues == null || influxTagValues.size() != tagValueIds.size()) {
            throw new IllegalArgumentException("incorrect tagValueIds");
        }
        if (!influxTagValues.isEmpty()) {
            InfluxBucket influxBucket = influxTagValues.get(0).getInfluxTag().getBucket();
            List<Long> requiredTagsForCurrentBucket = influxBucket.getTags().stream()
                    .map(InfluxTag::getId)
                    .toList();
            List<Long> givenTagIds = influxTagValues.stream()
                    .map(InfluxTagValue::getInfluxTag)
                    .map(InfluxTag::getId)
                    .toList();
            if (givenTagIds.size() != requiredTagsForCurrentBucket.size() || givenTagIds.stream().anyMatch(e -> !requiredTagsForCurrentBucket.contains(e))) {
                throw new IllegalArgumentException("give value for all tags in bucket");
            }
        }

        influxTagValues.forEach(tagValue -> {
            MeasAndInfluxTagValues measAndInfluxTagValue = new MeasAndInfluxTagValues();
            measAndInfluxTagValue.setMeasurements(measurements);
            measAndInfluxTagValue.setInfluxTagValue(tagValue);
            measurements.getMeasAndInfluxTagValues().add(measAndInfluxTagValuesService.save(measAndInfluxTagValue));
        });
        return measurementsRepository.save(measurements).getMeasAndInfluxTagValues();
    }
}
