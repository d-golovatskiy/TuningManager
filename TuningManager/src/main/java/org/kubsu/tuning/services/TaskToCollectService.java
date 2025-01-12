package org.kubsu.tuning.services;

import org.kubsu.tuning.domain.entities.TaskToCollect;
import org.kubsu.tuning.repositories.TasksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final TasksRepository tasksRepository;
    @Value("${tuning.collector.url}")
    private String collectorPath;
    private KafkaTemplate<String, TaskToCollect> kafkaTemplate;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TaskToCollectService(TasksRepository tasksRepository, KafkaTemplate<String, TaskToCollect> kafkaTemplate) {
        this.tasksRepository = tasksRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public HttpStatus changeTaskStatus(Long id, String status) throws IOException {
        if (!status.equals("started") && !status.equals("stopped") && !status.equals("finished")) {
            return HttpStatus.BAD_REQUEST;
        } else {
            TaskToCollect tmp = tasksRepository.findById(id).get();
            tmp.setStatus(status);

            CompletableFuture<SendResult<String, TaskToCollect>> future = kafkaTemplate.send("task-to-collect-queue-topic",
                    tmp.getId().toString(),
                    tmp);
            future.whenComplete((res, exception) -> {
                if (exception != null) {
                    log.error("Failed to send message: {}", exception.getMessage());
                } else {
                    log.info("Message sent successful: {}", res.getRecordMetadata());
                }
            });

            future.join();

            log.info("Return: {}", tmp.getId());

           /* final ObjectMapper mapper = new ObjectMapper();
            Request.Post(collectorPath + "/v1/tasksToCollect")
                    .addHeader("Content-Type", "application/json")
                    .bodyString(mapper.writeValueAsString(tmp), ContentType.APPLICATION_JSON)
                    .execute();
            tasksRepository.save(tmp);*/
            return HttpStatus.OK;
        }
    }

    public List<TaskToCollect> getTaskToCollect(Long id, String name, Timestamp dateStart, Timestamp dateEnd) {
        if (id == null && name == null && dateStart == null && dateEnd == null) {
            return tasksRepository.findAll();
        }
        if (id != null) {
            return List.of(tasksRepository.findById(id).get());
        } else {
            List<TaskToCollect> tasksToCollect = tasksRepository.findAll();
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
        tasksRepository.save(taskToCollect);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> patchTaskToCollect(TaskToCollect taskToCollect) throws IOException {
        TaskToCollect tmp = tasksRepository.findById(taskToCollect.getId()).get();
        if (!tmp.getStatus().equals(taskToCollect.getStatus())) {
            return ResponseEntity.ok(changeTaskStatus(tmp.getId(), taskToCollect.getStatus()));
        }
        tasksRepository.save(taskToCollect);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    public HttpStatus deleteTaskToCollect(Long id) {
        TaskToCollect tmp = tasksRepository.findById(id).get();
        if (!tmp.getStatus().equals("finished")) {
            return HttpStatus.BAD_REQUEST;
        } else {
            tasksRepository.deleteById(id);
            return HttpStatus.OK;
        }
    }

    public TaskToCollect save(TaskToCollect taskToCollect) {
        return tasksRepository.save(taskToCollect);
    }
}
