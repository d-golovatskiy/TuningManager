package org.kubsu.tuningmanager.services;

import org.kubsu.tuningmanager.entities.Sys;
import org.kubsu.tuningmanager.repositories.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysService {


    private final SystemRepository systemRepository;

    @Autowired
    public SysService(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    public List<Sys> getSys(Long id, String name){
        if(id == null && name == null){
            return systemRepository.findAll();
        }
        else {
            if (id == null) {
                return systemRepository.findAll().stream().filter(e -> e.getName().equals(name)).collect(Collectors.toList());
            } else {
                return List.of(systemRepository.findById(id).get());
            }
        }
    }

    public ResponseEntity<HttpStatus> addSys(Sys sys){
        systemRepository.save(sys);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> patchSys(Sys sys){
        systemRepository.save(sys);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteSys(Long id){
        systemRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
