package org.kubsu.tuning.controllers;

import org.kubsu.tuning.domain.dto.WorkloadProfileWriteDto;
import org.kubsu.tuning.domain.entities.Sys;
import org.kubsu.tuning.domain.entities.WorkloadProfile;
import org.kubsu.tuning.services.SysService;
import org.kubsu.tuning.services.WorkloadProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sys")
public class SysController {
    @Autowired
    private WorkloadProfileService workloadProfileService;

    private final SysService sysService;

    public SysController(SysService sysService) {
        this.sysService = sysService;
    }

    @GetMapping("")
    public List<Sys> getSys(@RequestParam(name = "id", required = false) Long id,
                            @RequestParam(name = "name", required = false) String name){
        return sysService.getSys(id,name);
    }

    @PatchMapping("")
    public ResponseEntity<HttpStatus> patchSys(@RequestBody Sys sys){
        return sysService.patchSys(sys);
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> addSys(@RequestBody Sys sys){
        return sysService.addSys(sys);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSys(@PathVariable(name = "id") Long id){
        return sysService.deleteSys(id);
    }

    @PostMapping("/{id}/workload-profiles")
    public WorkloadProfile createWorkloadProfiles(@PathVariable(name = "id") Long id, @RequestBody WorkloadProfileWriteDto workloadProfileWriteDto){
        return workloadProfileService.createByWriteDtoAndSysId(workloadProfileWriteDto, id);
    }

    @GetMapping("/{id}/workload-profiles")
    public List<WorkloadProfile> getWorkloadProfiles(@PathVariable(name = "id") Long id){
        return workloadProfileService.findBySysId(id);
    }

    @GetMapping("/{sysId}/workload-profiles/{profileId}")
    public WorkloadProfile getWorkloadProfiles(@PathVariable(name = "sysId") Long sysId, @PathVariable("profileId") Long profileId){
        return workloadProfileService.findById(profileId);
    }

    @DeleteMapping("/{sysId}/workload-profiles/{profileId}")
    public void deleteWorkloadProfile(@PathVariable(name = "sysId") Long sysId, @PathVariable("profileId") Long profileId){
        workloadProfileService.deleteById(profileId);
    }
}
