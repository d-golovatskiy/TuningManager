package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.AffectException;
import org.kubsu.tuning.repositories.AffectExceptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffectExceptionService {
    
    public final AffectExceptionRepository affectExceptionRepository;

    public AffectExceptionService(AffectExceptionRepository affectExceptionRepository) {
        this.affectExceptionRepository = affectExceptionRepository;
    }
    
    public List<AffectException> getAffectExceptions(Long id, Long sysId, Long taskId){
        if (id == null &&  sysId== null && taskId==null) {
            return affectExceptionRepository.findAll();
        }
        if (id != null){
            return List.of(affectExceptionRepository.findById(id).get()) ;
        }
        else{
            if (taskId!=null && sysId!=null){
                return List.of(affectExceptionRepository.findAffectExceptionByTaskIdAndSysId(taskId, sysId));
            }
            else{
                if(sysId==null){
                    return affectExceptionRepository.findAffectExceptionByTaskId(taskId);
                }
                else{
                    return affectExceptionRepository.findAffectExceptionBySysId(sysId);
                }
            }
        }
    }

    public ResponseEntity<HttpStatus> addAffectException(AffectException affectException){
        affectExceptionRepository.save(affectException);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteAffectException(Long id){
        affectExceptionRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> patchAffectException(AffectException affectException){
        if (affectExceptionRepository.findById(affectException.getId()).isPresent()){
            return (ResponseEntity<HttpStatus>) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
