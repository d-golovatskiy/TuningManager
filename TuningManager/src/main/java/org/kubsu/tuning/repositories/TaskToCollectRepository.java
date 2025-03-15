package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.ParamAndValue;
import org.kubsu.tuning.domain.entities.TaskToCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskToCollectRepository extends JpaRepository<TaskToCollect, Long> {
    @Query(value = "select t.* from tasks_to_collect t join sys_measurements sm on t.sys_meas_id = sm.id " +
            "where sm.meas_id in :measIds and sm.sys_id = :sysId " +
            "and (t.date_start between :startTime and :endTime " +
            "or t.date_end between :startTime and :endTime) " +
            "and t.collecting_status = 'OK'", nativeQuery = true)
    List<TaskToCollect> findAllBySystemIdAndCrossingDateRange(@Param(value = "startTime") Timestamp startTime,
                                                              @Param(value = "endTime") Timestamp endTime,
                                                              @Param(value = "measIds") List<Long> measIds,
                                                              @Param(value = "sysId") Long sysId);
}
