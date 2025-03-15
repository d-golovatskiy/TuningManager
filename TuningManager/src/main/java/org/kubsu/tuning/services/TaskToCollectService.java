package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.TaskToCollect;
import org.kubsu.tuning.repositories.TaskToCollectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TaskToCollectService {
    private final TaskToCollectRepository taskToCollectRepository;
    private KafkaTemplate<String, TaskToCollect> kafkaTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TaskToCollectService(TaskToCollectRepository taskToCollectRepository, KafkaTemplate<String, TaskToCollect> kafkaTemplate) {
        this.taskToCollectRepository = taskToCollectRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public HttpStatus changeTaskStatus(Long id, String status) throws IOException {
        if (!status.equals("started") && !status.equals("stopped") && !status.equals("finished")) {
            return HttpStatus.BAD_REQUEST;
        } else {
            TaskToCollect tmp = taskToCollectRepository.findById(id).get();
            tmp.setStatus(status);
            CompletableFuture<SendResult<String, TaskToCollect>> future = kafkaTemplate.send("task-to-collect-queue-topic"
                    , tmp.getId().toString()
                    , tmp);
            future.whenComplete((res, exception) -> {
                if (exception != null) {
                    log.error("Failed to send message: {}", exception.getMessage());
                } else {
                    log.info("Message sent successful: {}", res.getRecordMetadata());
                }
            });
            future.join();
            log.info("Return: {}", tmp.getId());
            return HttpStatus.OK;
        }
    }

    public List<TaskToCollect> getTaskToCollect(Long id, String name, Timestamp dateStart, Timestamp dateEnd) {
        if (id == null && name == null && dateStart == null && dateEnd == null) {
            return taskToCollectRepository.findAll();
        }
        if (id != null) {
            return List.of(taskToCollectRepository.findById(id).get());
        } else {
            List<TaskToCollect> tasksToCollect = taskToCollectRepository.findAll();
            if (name != null) {
                tasksToCollect = tasksToCollect.stream().filter(e -> e.getName().equals(name)).toList();
            }
            if (dateStart != null) {
                tasksToCollect = tasksToCollect.stream()
                        .filter(e -> e.getDateStart().before(dateStart))
                        .collect(Collectors.toList());
            }
            if (dateEnd != null) {
                tasksToCollect = tasksToCollect.stream()
                        .filter(e -> e.getDateEnd().after(dateEnd))
                        .collect(Collectors.toList());
            }
            return tasksToCollect;
        }
    }

    public ResponseEntity<HttpStatus> addTaskToCollect(TaskToCollect taskToCollect) {
        taskToCollect.setStatus("created");
        taskToCollectRepository.save(taskToCollect);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> patchTaskToCollect(TaskToCollect taskToCollect) throws IOException {
        TaskToCollect tmp = taskToCollectRepository.findById(taskToCollect.getId()).get();
        if (!tmp.getStatus().equals(taskToCollect.getStatus())) {
            return ResponseEntity.ok(changeTaskStatus(tmp.getId(), taskToCollect.getStatus()));
        }
        taskToCollectRepository.save(taskToCollect);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    public HttpStatus deleteTaskToCollect(Long id) {
        TaskToCollect tmp = taskToCollectRepository.findById(id).get();
        if (!tmp.getStatus().equals("finished")) {
            return HttpStatus.BAD_REQUEST;
        } else {
            taskToCollectRepository.deleteById(id);
            return HttpStatus.OK;
        }
    }

    public List<TaskToCollect> getAllTasksToCollectThatCrossingWithDateRange(Timestamp start, Timestamp end, List<Long> measIds, Long sysId) {
        return taskToCollectRepository.findAllBySystemIdAndCrossingDateRange(start, end, measIds, sysId);
    }

    public TaskToCollect save(TaskToCollect taskToCollect) {
        return taskToCollectRepository.save(taskToCollect);
    }
}
