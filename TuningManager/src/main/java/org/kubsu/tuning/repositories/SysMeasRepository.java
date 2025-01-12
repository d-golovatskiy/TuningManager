package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.SysMeas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMeasRepository extends JpaRepository<SysMeas, Long> {
}
