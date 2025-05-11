package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.InfluxTagValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfluxTagValuesRepository extends JpaRepository<InfluxTagValue, Long> {
}
