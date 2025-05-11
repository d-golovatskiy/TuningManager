package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.InfluxBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluxBucketRepository extends JpaRepository<InfluxBucket, Long> {
}
