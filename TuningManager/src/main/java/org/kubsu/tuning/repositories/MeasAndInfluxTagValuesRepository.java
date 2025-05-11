package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.MeasAndInfluxTagValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasAndInfluxTagValuesRepository extends JpaRepository<MeasAndInfluxTagValues, Long> {
    List<MeasAndInfluxTagValues> findAllByMeasurementsIdAndInfluxTagValueIdIn(Long measurementsId, List<Long> influxTagValueIds);
}
