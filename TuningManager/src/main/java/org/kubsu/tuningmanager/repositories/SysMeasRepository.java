package org.kubsu.tuningmanager.repositories;

import org.kubsu.tuningmanager.entities.Sys;
import org.kubsu.tuningmanager.entities.SysMeas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMeasRepository extends JpaRepository<SysMeas, Long> {
}
