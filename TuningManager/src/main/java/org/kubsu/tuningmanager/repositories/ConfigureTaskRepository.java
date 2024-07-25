package org.kubsu.tuningmanager.repositories;

import org.kubsu.tuningmanager.entities.ConfigureTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigureTaskRepository extends JpaRepository<ConfigureTask,Long> {
}
