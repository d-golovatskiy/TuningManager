package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.Config;
import org.kubsu.tuning.domain.entities.Sys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config,Long> {
    Config findBySys(Sys sys);
}
