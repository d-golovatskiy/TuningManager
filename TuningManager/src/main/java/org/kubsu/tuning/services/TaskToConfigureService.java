package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.TaskToConfigure;
import org.kubsu.tuning.domain.entities.Config;
import org.kubsu.tuning.domain.entities.Measurements;
import org.kubsu.tuning.domain.entities.RequestToConfigure;
import org.kubsu.tuning.domain.entities.TaskToCollect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TaskToConfigureService {
    @Autowired
    private TaskToCollectService taskToCollectService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private KafkaTemplate<String, TaskToConfigure> kafkaTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public TaskToConfigure createByRequestToConfigure(RequestToConfigure requestToConfigure) {
        Timestamp start = requestToConfigure.getConfiguringPeriodStartDate();
        Timestamp end = requestToConfigure.getConfiguringPeriodEndDate();
        Measurements measurements = requestToConfigure.getMeasurements();
        Long measId = measurements.getId();
        Long sysId = requestToConfigure.getSys().getId();
        List<TaskToCollect> tasksToCollect = taskToCollectService.getAllTasksToCollectThatCrossingWithDateRange(start, end, measId, sysId);
        List<String> fileNames = new ArrayList<>();
        tasksToCollect.stream().forEach(e -> {
            fileNames.add("task_to_collect_id_" + e.getId() + ".csv");
        });
        TaskToConfigure taskToConfigure = new TaskToConfigure();
        taskToConfigure.setFilenames(fileNames);
        taskToConfigure.setConfigurePeriodStartDate(start);
        taskToConfigure.setConfigurePeriodEndDate(end);
        taskToConfigure.setMeasurements(measurements);
        Config config = configService.findBySys(requestToConfigure.getSys());
        taskToConfigure.setConfig(config);
        taskToConfigure.setRequestToConfigureId(requestToConfigure.getId());
        taskToConfigure.setWorkloadProfile(requestToConfigure.getWorkloadProfile());
        return taskToConfigure;
    }

    public void sendTaskToConfigure(TaskToConfigure taskToConfigure) {
        CompletableFuture<SendResult<String, TaskToConfigure>> future = kafkaTemplate.send("task-to-configure-queue-topic",
                taskToConfigure.getRequestToConfigureId().toString(),
                taskToConfigure);
        future.whenComplete((res, exception) -> {
            if (exception != null) {
                log.error("Failed to send message: {}", exception.getMessage());
            } else {
                log.info("Message sent successful: {}", res.getRecordMetadata());
            }
        });

        future.join();

        log.info("Return: {}",  taskToConfigure.getRequestToConfigureId());
    }
}
