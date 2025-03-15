package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.ConfigureRequestMeasurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigureRequestMeasurementsRepository extends JpaRepository<ConfigureRequestMeasurements, Long> {
}
