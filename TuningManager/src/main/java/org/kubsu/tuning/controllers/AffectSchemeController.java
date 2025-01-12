package org.kubsu.tuning.controllers;

import org.kubsu.tuning.domain.entities.AffectingScheme;
import org.kubsu.tuning.services.AffectSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/affectScheme")
public class AffectSchemeController {

    private final AffectSchemeService affectSchemeService;

    @Autowired
    public AffectSchemeController(AffectSchemeService affectSchemeService) {
        this.affectSchemeService = affectSchemeService;
    }

    @GetMapping("")
    public List<AffectingScheme> getAffects(@RequestParam(name="affected_id", required = false) Long affectedId,
                                            @RequestParam(name = "affecting_id",required = false) Long affectingId,
                                            @RequestParam(name = "affect_scheme_id", required = false) Long id){
    return   affectSchemeService.getAffectingSystems(id,affectedId,affectingId);

    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> addAffect(@RequestBody AffectingScheme affectingScheme){
        return affectSchemeService.addAffectScheme(affectingScheme);
    }

    @PatchMapping("")
    public ResponseEntity<HttpStatus> patchAffect(@RequestBody AffectingScheme affectingScheme){
        return affectSchemeService.patchAffectScheme(affectingScheme);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAffect(@PathVariable(name = "id") Long id){
        return affectSchemeService.deleteAffectScheme(id);
    }
}
