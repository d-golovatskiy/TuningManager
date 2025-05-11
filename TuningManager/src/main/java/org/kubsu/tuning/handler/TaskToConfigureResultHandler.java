package org.kubsu.tuning.handler;

import org.kubsu.tuning.domain.dto.TaskToConfigureResultDto;
import org.kubsu.tuning.domain.entities.RequestToConfigure;
import org.kubsu.tuning.services.RequestToConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@KafkaListener(topics = "task-to-configure-result-topic")
public class TaskToConfigureResultHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RequestToConfigureService requestToConfigureService;

    @KafkaHandler
    public void handleTaskToConfigureResult(TaskToConfigureResultDto taskToConfigureResultDto) {
        RequestToConfigure requestToConfigure = requestToConfigureService.findById(taskToConfigureResultDto.getTaskId()).orElse(null);
        if (requestToConfigure != null) {
            requestToConfigure.setStatus("finished");
            requestToConfigure.setConfigureStatus(taskToConfigureResultDto.getTaskResult().toString());
            requestToConfigureService.save(requestToConfigure);
        } else {
            LOGGER.error("unknown request to configure result with id: {}", requestToConfigure.getId());
        }
    }
}
