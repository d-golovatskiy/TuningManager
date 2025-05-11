package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.InfluxTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluxTagRepository extends JpaRepository<InfluxTag, Long> {
}
