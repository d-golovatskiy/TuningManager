package org.kubsu.tuningmanager.controllers;

import org.kubsu.tuningmanager.entities.Measurements;
import org.kubsu.tuningmanager.services.MeasurementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/meas")
public class MeasurementsController {

    private final MeasurementService measurementService;

    public MeasurementsController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("")
    public List<Measurements> getMeas(@RequestParam(name = "id", required = false) Long id){
       return measurementService.getMeas(id);
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> addMeas(@RequestBody Measurements measurements){
        return measurementService.addMeas(measurements);
    }

    @PatchMapping("")
    public ResponseEntity<HttpStatus> patchMeas(@RequestBody Measurements measurements){
        return measurementService.patchMeas(measurements);
    }

    @DeleteMapping("")
    public ResponseEntity<HttpStatus> deleteMeas(@RequestParam(name = "id") Long id){
        return measurementService.deleteMeas(id);
    }


}
