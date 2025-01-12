package org.kubsu.tuning.controllers;

import org.kubsu.tuning.domain.entities.AffectException;
import org.kubsu.tuning.services.AffectExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/affectException")
public class AffectExceptionController {

    private final AffectExceptionService affectExceptionService;

    @Autowired
    public AffectExceptionController(AffectExceptionService affectExceptionService) {
        this.affectExceptionService = affectExceptionService;
    }

    @GetMapping("")
    public List<AffectException> getAffectExceptions(@RequestParam(name="task_id", required = false) Long taskId,
                                                     @RequestParam(name = "sys_id",required = false) Long sysId,
                                                     @RequestParam(name = "affect_exc_id", required = false) Long id){
        return   affectExceptionService.getAffectExceptions(id,taskId,sysId);

    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> addAffectExc(@RequestBody AffectException affectException){
        return affectExceptionService.addAffectException(affectException);
    }

    @PatchMapping("")
    public ResponseEntity<HttpStatus> patchAffectExc(@RequestBody AffectException affectException){
        return affectExceptionService.patchAffectException(affectException);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAffectExc(@PathVariable(name = "id") Long id){
        return affectExceptionService.deleteAffectException(id);
    }
}
