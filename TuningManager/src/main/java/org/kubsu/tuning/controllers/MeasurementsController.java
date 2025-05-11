package org.kubsu.tuning.controllers;

import org.kubsu.tuning.domain.entities.MeasAndInfluxTagValues;
import org.kubsu.tuning.domain.entities.Measurements;
import org.kubsu.tuning.services.MeasurementService;
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
       return measurementService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> addMeas(@RequestBody Measurements measurements){
        return measurementService.save(measurements);
    }

    @PatchMapping("")
    public ResponseEntity<HttpStatus> patchMeas(@RequestBody Measurements measurements){
        return measurementService.update(measurements);
    }

    @DeleteMapping("")
    public ResponseEntity<HttpStatus> deleteMeas(@RequestParam(name = "id") Long id){
        return measurementService.deleteById(id);
    }

    @PostMapping("/{measId}/add-tag-values")
    public List<MeasAndInfluxTagValues> setTagValuesByIds(@PathVariable("measId") Long measId, @RequestBody List<Long> tagValueIds){
        return measurementService.setTagValuesByIds(measId, tagValueIds);
    }
}
