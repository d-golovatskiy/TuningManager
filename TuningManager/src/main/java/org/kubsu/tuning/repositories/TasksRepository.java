package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.TaskToCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<TaskToCollect, Long> {
}
