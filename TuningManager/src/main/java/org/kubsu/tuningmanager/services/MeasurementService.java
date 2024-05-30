package org.kubsu.tuningmanager.services;

import org.kubsu.tuningmanager.entities.Measurements;
import org.kubsu.tuningmanager.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {

    private final MeasurementsRepository measurementsRepository;

    @Autowired
    public MeasurementService(MeasurementsRepository measurementsRepository) {
        this.measurementsRepository = measurementsRepository;
    }

    public List<Measurements> getMeas(Long id){
        if (id == null){
            return measurementsRepository.findAll();
        }
        return List.of(measurementsRepository.findById(id).get());
    }

    public ResponseEntity<HttpStatus> addMeas(Measurements measurements){
        measurementsRepository.save(measurements);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> patchMeas(Measurements measurements){
        measurementsRepository.save(measurements);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteMeas(Long id){
        measurementsRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
