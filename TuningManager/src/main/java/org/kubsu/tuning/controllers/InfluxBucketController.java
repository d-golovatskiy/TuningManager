package org.kubsu.tuning.controllers;

import org.kubsu.tuning.domain.entities.InfluxBucket;
import org.kubsu.tuning.domain.entities.InfluxTag;
import org.kubsu.tuning.domain.entities.InfluxTagValue;
import org.kubsu.tuning.services.InfluxBucketService;
import org.kubsu.tuning.services.InfluxTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/buckets")
public class InfluxBucketController {
    @Autowired
    private InfluxBucketService influxBucketService;

    @Autowired
    private InfluxTagService influxTagService;

    @PostMapping("")
    public InfluxBucket createOrUpdateBucket(@RequestBody InfluxBucket bucket) {
        return influxBucketService.save(bucket);
    }

    @GetMapping("")
    public List<InfluxBucket> getAllBuckets() {
        return influxBucketService.findAll();
    }

    @GetMapping("/{id}")
    public InfluxBucket findBucketById(@PathVariable("id") Long id) {
        return influxBucketService.findById(id);
    }

    @PostMapping("/{bucketId}/tags")
    public InfluxBucket createOrUpdateTags(@PathVariable("bucketId") Long bucketId, @RequestBody List<InfluxTag> tags) {
        return influxBucketService.addOrUpdateTagForBucket(bucketId, tags);
    }

    @PostMapping("/{bucketId}/tags/{tagId}/values")
    public InfluxTag createOrUpdateTagValues(@PathVariable("bucketId") Long bucketId, @PathVariable("tagId") Long tagId,  @RequestBody List<InfluxTagValue> tagValues) {
        return influxTagService.addOrUpdateTagValuesForTag(tagId, tagValues);
    }
}
