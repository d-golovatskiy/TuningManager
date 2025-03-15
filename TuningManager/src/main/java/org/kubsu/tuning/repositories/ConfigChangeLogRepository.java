package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.ConfigChangeLog;
import org.kubsu.tuning.domain.entities.ParamAndValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigChangeLogRepository extends JpaRepository<ConfigChangeLog, Long> {
    List<ConfigChangeLog> findByParam(ParamAndValue paramAndValue);
}
