package org.kubsu.tuning.controllers;

import org.kubsu.tuning.domain.TaskToConfigure;
import org.kubsu.tuning.domain.dto.RequestToConfigureWriteDto;
import org.kubsu.tuning.domain.entities.RequestToConfigure;
import org.kubsu.tuning.services.RequestToConfigureService;
import org.kubsu.tuning.services.TaskToConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/requests-to-configure")
public class RequestToConfigureController {
    @Autowired
    private RequestToConfigureService requestToConfigureService;
    @Autowired
    private TaskToConfigureService taskToConfigureService;

    @PostMapping("")
    public RequestToConfigure create(@RequestBody RequestToConfigureWriteDto requestToConfigureWriteDto) {
        return requestToConfigureService.createAndSaveByRequestToConfigureWriteDto(requestToConfigureWriteDto);
    }

    @GetMapping("")
    public List<RequestToConfigure> findAll() {
        return requestToConfigureService.findAll();
    }

    @GetMapping("/{id}")
    public RequestToConfigure findById(@PathVariable Long id) {
        return requestToConfigureService.findById(id).orElse(null);
    }

    @PostMapping("/{id}/start")
    public RequestToConfigure startTaskToConfigure(@PathVariable Long id) {
        RequestToConfigure requestToConfigure = requestToConfigureService.findById(id).orElse(null);
        if (requestToConfigure != null) {
            requestToConfigure.setStatus("started");
            TaskToConfigure taskToConfigure = taskToConfigureService.createByRequestToConfigure(requestToConfigure);
            taskToConfigureService.sendTaskToConfigure(taskToConfigure);
            requestToConfigure.setTaskToConfigure(taskToConfigure);
        }
        return requestToConfigureService.save(requestToConfigure);
    }
}
