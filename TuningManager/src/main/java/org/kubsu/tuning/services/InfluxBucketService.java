package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.InfluxBucket;
import org.kubsu.tuning.domain.entities.InfluxTag;
import org.kubsu.tuning.repositories.InfluxBucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfluxBucketService {
    @Autowired
    private InfluxBucketRepository influxBucketRepository;
    @Autowired
    private InfluxTagService influxTagService;

    public InfluxBucket save(InfluxBucket influxBucket) {
        return influxBucketRepository.save(influxBucket);
    }

    public InfluxBucket findById(Long id) {
        return influxBucketRepository.findById(id).orElse(null);
    }

    public List<InfluxBucket> findAll() {
        return influxBucketRepository.findAll();
    }

    public InfluxBucket addOrUpdateTagForBucket(Long bucketId, List<InfluxTag> influxTags) {
        InfluxBucket bucket = influxBucketRepository.findById(bucketId).orElseThrow(() -> new IllegalArgumentException("Bucket not found"));
        influxTags.forEach(influxTag -> {
            if (influxTag.getId() != null && !influxTag.getBucket().getId().equals(bucketId)) {
                throw new IllegalArgumentException("Bucket id mismatch");
            }
            if (bucket.getTags().stream()
                    .filter(tag -> tag.getName().equals(influxTag.getName()) && !tag.getId().equals(influxTag.getId()))
                    .count() != 0) {
                throw new IllegalArgumentException("Bucket already exists");
            }
            bucket.getTags().add(influxTag);
            influxTag.setBucket(bucket);
        });
        influxBucketRepository.save(bucket);
        bucket.getTags().forEach(tag -> {
            if (!tag.getTagValues().isEmpty()) {
                influxTagService.addOrUpdateTagValuesForTag(tag.getId(), tag.getTagValues());
            }
        });
        return bucket;
    }
}
