package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.WorkloadType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkloadTypeRepository extends JpaRepository<WorkloadType, Long> {
    WorkloadType findWorkloadTypeByWorkloadProfileIdAndName(Long workloadProfileId, String name);
    List<WorkloadType> findWorkloadTypeByWorkloadProfileId(Long workloadId);
}
