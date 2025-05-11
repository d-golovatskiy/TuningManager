package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.InfluxTag;
import org.kubsu.tuning.domain.entities.InfluxTagValue;
import org.kubsu.tuning.repositories.InfluxTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfluxTagService {
    @Autowired
    private InfluxTagRepository influxTagRepository;

    public InfluxTag save(InfluxTag influxTag) {
        return influxTagRepository.save(influxTag);
    }

    public InfluxTag findById(Long id) {
        return influxTagRepository.findById(id).orElse(null);
    }

    public List<InfluxTag> findAll() {
        return influxTagRepository.findAll();
    }

    public InfluxTag addOrUpdateTagValuesForTag(Long tagId, List<InfluxTagValue> influxTagValues) {
        InfluxTag tag = findById(tagId);
        if (tag == null) {
            throw new IllegalArgumentException("Tag not found");
        }

        List<InfluxTagValue> influxTagValuesToSave = new ArrayList<>();
        influxTagValues.forEach(influxTagValue -> {
            if (influxTagValue.getId() != null
                    && influxTagValue.getInfluxTag() != null
                    && influxTagValue.getInfluxTag().getId() != null
                    &&  !influxTagValue.getInfluxTag().getId().equals(tagId)) {
                throw new IllegalArgumentException("Tag id mismatch");
            }
            if (tag.getTagValues().stream()
                    .filter(e -> e.getInfluxTag() != null && e.getValue().equals(influxTagValue.getValue()))
                    .count() != 0) {
                throw new IllegalArgumentException("TagValue with same value already exists");
            }
            influxTagValuesToSave.add(influxTagValue);
        });
        influxTagValues.forEach(influxTagValue -> {
            influxTagValue.setInfluxTag(tag);
        });
        tag.getTagValues().addAll(influxTagValuesToSave);
        return influxTagRepository.save(tag);
    }
}
