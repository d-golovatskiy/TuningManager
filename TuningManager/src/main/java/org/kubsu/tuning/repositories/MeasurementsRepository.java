package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Long> {
}
