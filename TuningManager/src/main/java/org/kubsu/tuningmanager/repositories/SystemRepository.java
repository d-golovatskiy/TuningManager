package org.kubsu.tuningmanager.repositories;

import org.kubsu.tuningmanager.entities.Sys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends JpaRepository<Sys, Long> {
}
