package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.Config;
import org.kubsu.tuning.domain.entities.ParamAndValue;
import org.kubsu.tuning.repositories.ParamAndValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParamAndValueService {
    @Autowired
    private ParamAndValueRepository paramAndValueRepository;

    public Optional<ParamAndValue> findById(Integer id) {
        return paramAndValueRepository.findById(id);
    }

    public List<ParamAndValue> findAll() {
        return paramAndValueRepository.findAll();
    }

    public List<ParamAndValue> findAllByConfig(Config config) {
        return paramAndValueRepository.findAllByConfig(config);
    }

    public ParamAndValue save(ParamAndValue paramAndValue) {
        return paramAndValueRepository.save(paramAndValue);
    }

    public List<ParamAndValue> saveAll(List<ParamAndValue> paramAndValues) {
        return paramAndValueRepository.saveAll(paramAndValues);
    }

    public void deleteById(Integer id) {
        paramAndValueRepository.deleteById(id);
    }
}
