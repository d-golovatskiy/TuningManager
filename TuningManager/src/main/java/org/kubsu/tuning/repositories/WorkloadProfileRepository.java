package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.WorkloadProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkloadProfileRepository extends JpaRepository<WorkloadProfile, Long> {
    WorkloadProfile findWorkloadProfileBySysIdAndName(Long sysId, String name);
    List<WorkloadProfile> findWorkloadProfileBySysId(Long sysId);
}
