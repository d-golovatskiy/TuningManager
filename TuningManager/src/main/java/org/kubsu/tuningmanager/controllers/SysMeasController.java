package org.kubsu.tuningmanager.controllers;

import org.kubsu.tuningmanager.entities.SysMeas;
import org.kubsu.tuningmanager.services.SysMeasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sysMeas")
public class SysMeasController {

    private final SysMeasService sysMeasService;

    public SysMeasController(SysMeasService sysMeasService) {
        this.sysMeasService = sysMeasService;
    }

    @GetMapping("")
    public List<SysMeas> getSysMeas(@RequestParam(name = "id", required = false) Long id){
        return sysMeasService.getSysMeas(id);
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> addSysMeas(@RequestBody SysMeas sysMeas){
        return sysMeasService.addSysMeas(sysMeas);
    }

    @PatchMapping("")
    public ResponseEntity<HttpStatus> patchSysMeas(@RequestBody SysMeas sysMeas){
        return sysMeasService.patchSysMeas(sysMeas);
    }

    @DeleteMapping("")
    public ResponseEntity<HttpStatus> deleteSysMeas(@RequestParam(name = "id") Long id){
        return sysMeasService.deleteSysMeas(id);
    }

}
