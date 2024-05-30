package org.kubsu.tuningmanager.repositories;

import org.kubsu.tuningmanager.entities.TaskToCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<TaskToCollect, Long> {
}
