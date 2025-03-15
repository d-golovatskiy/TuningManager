package org.kubsu.tuning.repositories;

import org.kubsu.tuning.domain.entities.Config;
import org.kubsu.tuning.domain.entities.ParamAndValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParamAndValueRepository extends JpaRepository<ParamAndValue, Integer> {
    List<ParamAndValue> findAllByConfig(Config config);
}
