package org.kubsu.tuningmanager.repositories;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.kubsu.tuningmanager.entities.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Long> {
}
