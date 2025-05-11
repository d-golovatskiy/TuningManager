package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.dto.WorkloadProfileWriteDto;
import org.kubsu.tuning.domain.entities.Sys;
import org.kubsu.tuning.domain.entities.WorkloadProfile;
import org.kubsu.tuning.domain.entities.WorkloadType;
import org.kubsu.tuning.repositories.WorkloadProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkloadProfileService {
    @Autowired
    private WorkloadProfileRepository workloadProfileRepository;
    @Autowired
    private SysService sysService;
    @Autowired
    private WorkloadTypeService workloadTypeService;

    public WorkloadProfile findById(Long id) {
        return workloadProfileRepository.findById(id).orElse(null);
    }

    public Optional<WorkloadProfile> findBySysIdAndName(Long sysId, String name) {
        return Optional.ofNullable(workloadProfileRepository.findWorkloadProfileBySysIdAndName(sysId, name));
    }

    public List<WorkloadProfile> findBySysId(Long sysId) {
        return workloadProfileRepository.findWorkloadProfileBySysId(sysId);
    }

    public WorkloadProfile save(WorkloadProfile workloadProfile) {
        return workloadProfileRepository.save(workloadProfile);
    }

    public void deleteById(Long id) {
        workloadProfileRepository.deleteById(id);
    }

    public WorkloadProfile createByWriteDtoAndSysId(WorkloadProfileWriteDto workloadProfileWriteDto, Long sysId) {
        Sys sys = sysService.findById(sysId).orElseThrow(() -> new IllegalArgumentException("sys not found"));
        WorkloadProfile workloadProfile = findBySysIdAndName(sysId, workloadProfileWriteDto.getName()).orElse(new WorkloadProfile());
        if (workloadProfile.getId() == null) {
            workloadProfile.setName(workloadProfileWriteDto.getName());
            workloadProfile.setDescription(workloadProfileWriteDto.getDescription());
            workloadProfile.setSys(sys);
            sys.getWorkloadProfiles().add(workloadProfile);
            workloadProfileRepository.save(workloadProfile);
        }
        workloadProfileWriteDto.getWorkloadTypes().forEach(e -> {
            WorkloadType workloadType = workloadTypeService.findByWorkloadProfileIdAndName(workloadProfile.getId(), e.getName()).orElse(new WorkloadType());
            workloadType.setName(e.getName());
            workloadType.setLeftBoundary(e.getLeftBoundary());
            workloadType.setRightBoundary(e.getRightBoundary());
            if (workloadType.getId() == null) {
                workloadType.setWorkloadProfile(workloadProfile);
                workloadProfile.getWorkloadTypes().add(workloadType);
            }
            workloadTypeService.save(workloadType);
        });
        return workloadProfile;
    }
}
