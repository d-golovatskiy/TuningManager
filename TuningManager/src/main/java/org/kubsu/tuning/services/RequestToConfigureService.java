package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.dto.RequestToConfigureWriteDto;
import org.kubsu.tuning.domain.entities.ConfigureRequestMeasurements;
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
        List<SysMeas> sysMeasList = sysMeasService.findAllByMeasIds(requestToConfigureWriteDto.getMeasurementIds(), requestToConfigureWriteDto.getSysId());
        RequestToConfigure requestToConfigure = new RequestToConfigure();
        requestToConfigure.setSys(sysMeasList.get(0).getSys());
        requestToConfigure.setConfiguringPeriodStartDate(requestToConfigureWriteDto.getConfigurePeriodStartDate());
        requestToConfigure.setConfiguringPeriodEndDate(requestToConfigureWriteDto.getConfigurePeriodEndDate());
        requestToConfigure.setDescription(requestToConfigureWriteDto.getDescription());
        requestToConfigure.setStatus("created");
        sysMeasList.forEach(sysMeas -> {
            Measurements measurements = sysMeas.getMeasurements();
            ConfigureRequestMeasurements configureRequestMeasurements = new ConfigureRequestMeasurements();
            configureRequestMeasurements.setMeasurements(measurements);
            configureRequestMeasurements.setRequestToConfigure(requestToConfigure);
            requestToConfigure.getConfigureRequestMeasurements().add(configureRequestMeasurements);
        });
        return requestToConfigureRepository.save(requestToConfigure);
    }
}
