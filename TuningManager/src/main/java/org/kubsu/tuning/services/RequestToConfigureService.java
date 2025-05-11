package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.dto.RequestToConfigureWriteDto;
import org.kubsu.tuning.domain.entities.Measurements;
import org.kubsu.tuning.domain.entities.RequestToConfigure;
import org.kubsu.tuning.domain.entities.SysMeas;
import org.kubsu.tuning.repositories.RequestToConfigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestToConfigureService {
    @Autowired
    private RequestToConfigureRepository requestToConfigureRepository;
    @Autowired
    private SysMeasService sysMeasService;
    @Autowired
    private WorkloadProfileService workloadProfileService;

    public RequestToConfigure save(RequestToConfigure requestToConfigure) {
        return requestToConfigureRepository.save(requestToConfigure);
    }

    public List<RequestToConfigure> saveAll(List<RequestToConfigure> requestsToConfigure) {
        return requestToConfigureRepository.saveAll(requestsToConfigure);
    }

    public List<RequestToConfigure> findAll() {
        return requestToConfigureRepository.findAll();
    }

    public Optional<RequestToConfigure> findById(Long id) {
        return requestToConfigureRepository.findById(id);
    }

    public void delete(RequestToConfigure requestToConfigure) {
        requestToConfigureRepository.delete(requestToConfigure);
    }

    public RequestToConfigure createAndSaveByRequestToConfigureWriteDto(RequestToConfigureWriteDto requestToConfigureWriteDto) {
        SysMeas sysMeas = sysMeasService.findByMeasId(requestToConfigureWriteDto.getMeasurementId(), requestToConfigureWriteDto.getSysId());
        RequestToConfigure requestToConfigure = new RequestToConfigure();
        requestToConfigure.setMeasurements(sysMeas.getMeasurements());
        requestToConfigure.setSys(sysMeas.getSys());
        requestToConfigure.setConfiguringPeriodStartDate(requestToConfigureWriteDto.getConfigurePeriodStartDate());
        requestToConfigure.setConfiguringPeriodEndDate(requestToConfigureWriteDto.getConfigurePeriodEndDate());
        requestToConfigure.setDescription(requestToConfigureWriteDto.getDescription());
        requestToConfigure.setStatus("created");
        if (workloadProfileService.findBySysId(requestToConfigureWriteDto.getSysId()).stream()
                .noneMatch(workloadProfile -> workloadProfile.getId().equals(requestToConfigureWriteDto.getWorkloadProfileId()))){
            throw new IllegalArgumentException("sys not have workload profile with given id");
        }
        return requestToConfigureRepository.save(requestToConfigure);
    }
}
