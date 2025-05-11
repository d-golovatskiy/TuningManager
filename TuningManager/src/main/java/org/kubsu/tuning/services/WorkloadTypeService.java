package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.WorkloadType;
import org.kubsu.tuning.repositories.WorkloadTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkloadTypeService {
    @Autowired
    private WorkloadTypeRepository workloadTypeRepository;

    public WorkloadType findById(Long id) {
        return workloadTypeRepository.findById(id).orElse(null);
    }

    public Optional<WorkloadType> findByWorkloadProfileIdAndName(Long workloadProfileId, String name) {
        return Optional.ofNullable(workloadTypeRepository.findWorkloadTypeByWorkloadProfileIdAndName(workloadProfileId, name));
    }

    public List<WorkloadType> findByWorkloadProfileId(Long workloadProfileId) {
        return workloadTypeRepository.findWorkloadTypeByWorkloadProfileId(workloadProfileId);
    }

    public WorkloadType save(WorkloadType workloadType) {
        return workloadTypeRepository.save(workloadType);
    }

    public void delete(WorkloadType workloadType) {
        workloadTypeRepository.delete(workloadType);
    }
}
