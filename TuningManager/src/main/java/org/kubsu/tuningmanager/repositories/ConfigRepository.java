package org.kubsu.tuningmanager.repositories;

import org.kubsu.tuningmanager.entities.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config,Long> {
}
