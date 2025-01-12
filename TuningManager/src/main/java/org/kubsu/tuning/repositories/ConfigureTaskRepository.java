package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.ConfigureTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigureTaskRepository extends JpaRepository<ConfigureTask,Long> {
}
