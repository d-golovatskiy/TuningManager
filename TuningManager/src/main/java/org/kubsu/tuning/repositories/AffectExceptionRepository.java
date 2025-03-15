package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.AffectException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffectExceptionRepository extends JpaRepository<AffectException, Long> {
    List<AffectException> findAffectExceptionBySysId(Long sysId);
    List<AffectException> findAffectExceptionByTaskId(Long taskId);
    AffectException findAffectExceptionByTaskIdAndSysId(Long taskId, Long sysId);
}
