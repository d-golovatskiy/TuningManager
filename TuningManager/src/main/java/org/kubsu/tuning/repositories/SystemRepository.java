package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.Sys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends JpaRepository<Sys, Long> {
}
