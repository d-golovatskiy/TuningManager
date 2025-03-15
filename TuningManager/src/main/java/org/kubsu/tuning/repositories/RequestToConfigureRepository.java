package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.RequestToConfigure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestToConfigureRepository extends JpaRepository<RequestToConfigure, Long> {
}
