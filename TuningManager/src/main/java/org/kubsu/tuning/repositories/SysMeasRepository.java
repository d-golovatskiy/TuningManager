package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.SysMeas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMeasRepository extends JpaRepository<SysMeas, Long> {
    List<SysMeas> findAllByMeasIdInAndSysId(List<Long> measIds, Long sysId);
}
