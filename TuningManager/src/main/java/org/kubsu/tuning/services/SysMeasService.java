package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.SysMeas;
import org.kubsu.tuning.repositories.SysMeasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMeasService {

    private final SysMeasRepository sysMeasRepository;

    @Autowired
    public SysMeasService(SysMeasRepository sysMeasRepository) {
        this.sysMeasRepository = sysMeasRepository;
    }

    public List<SysMeas> getSysMeas(Long id){
        if (id == null){
            return sysMeasRepository.findAll();
        }
        return List.of(sysMeasRepository.findById(id).get());
    }

    public ResponseEntity<HttpStatus> addSysMeas(SysMeas sysMeas){
        sysMeasRepository.save(sysMeas);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> patchSysMeas(SysMeas sysMeas){
        sysMeasRepository.save(sysMeas);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteSysMeas(Long id){
        //sysMeasRepository.delete(sysMeasRepository.findById(id).get());
        sysMeasRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public List<SysMeas> findAllByMeasIds(List<Long> measIds, Long sysId){
        return sysMeasRepository.findAllByMeasIdInAndSysId(measIds, sysId);
    }
}
