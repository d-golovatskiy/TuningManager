package org.kubsu.tuningmanager.services;


import lombok.AllArgsConstructor;
import org.kubsu.tuningmanager.entities.AffectingScheme;
import org.kubsu.tuningmanager.repositories.AffectingSchemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AffectSchemeService {
    private final AffectingSchemeRepository affectingSchemeRepository;

    @Autowired
    public AffectSchemeService(AffectingSchemeRepository affectingSchemeRepository) {
        this.affectingSchemeRepository = affectingSchemeRepository;
    }

    public List<AffectingScheme> getAffectingSystems(Long id, Long affectedId, Long affectingId){
        if (id == null && affectingId == null && affectedId==null) {
            return affectingSchemeRepository.findAll();
        }
        if (id != null){
            return List.of(affectingSchemeRepository.findById(id).get()) ;
        }
        else{
            if (affectedId!=null && affectingId!=null){
               return affectingSchemeRepository.findAffectingSchemeByAffectedIdAndAffectingId(affectedId, affectingId);
            }
            else{
                if(affectingId==null){
                    return affectingSchemeRepository.findAffectingSchemeByAffectedId(affectedId);
                }
                else{
                    return affectingSchemeRepository.findAffectingSchemeByAffectingId(affectingId);
                }
            }
        }
    }

    public ResponseEntity<HttpStatus> addAffectScheme(AffectingScheme affectingScheme){
        affectingSchemeRepository.save(affectingScheme);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteAffectScheme(Long id){
        affectingSchemeRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> patchAffectScheme(AffectingScheme affectingScheme){
        if (affectingSchemeRepository.findById(affectingScheme.getId()).isPresent()){
            return (ResponseEntity<HttpStatus>) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
