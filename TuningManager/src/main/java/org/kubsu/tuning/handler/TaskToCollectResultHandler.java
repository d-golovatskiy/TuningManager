package org.kubsu.tuning.handler;

import org.kubsu.tuning.domain.dto.TaskToCollectResultDto;
import org.kubsu.tuning.domain.entities.TaskToCollect;
import org.kubsu.tuning.services.TaskToCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@KafkaListener(topics = "task-to-collect-result-topic")
public class TaskToCollectResultHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskToCollectService taskToCollectService;

    @KafkaHandler
    public void handleTaskToCollectResult(TaskToCollectResultDto taskToCollectResultDto) {
        TaskToCollect taskToCollect = taskToCollectService.getTaskToCollect(taskToCollectResultDto.getTaskId(), null,null,null).get(0);

        taskToCollect.setStatus("finished");
        taskToCollect.setCollectingStatus(taskToCollectResultDto.getTaskToCollectResult().toString());

        taskToCollectService.save(taskToCollect);
    }
}
