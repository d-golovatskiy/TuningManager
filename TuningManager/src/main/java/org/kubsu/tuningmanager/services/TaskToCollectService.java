package org.kubsu.tuningmanager.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.kubsu.tuningmanager.entities.TaskToCollect;
import org.kubsu.tuningmanager.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskToCollectService {

    private final TasksRepository tasksRepository;

    @Value("${tuning.collector.url}")
    private String collectorPath;

    @Autowired
    public TaskToCollectService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public HttpStatus changeTaskStatus(Long id, String status) throws IOException {
        if (!status.equals("started")
                && !status.equals("stopped")
                && !status.equals("finished")
                || status.equals("created")) {
            return HttpStatus.BAD_REQUEST;
        } else {

            TaskToCollect tmp = tasksRepository.findById(id).get();
            tmp.setStatus(status);
            final ObjectMapper mapper = new ObjectMapper();
            Request.Post(collectorPath + "/v1/tasksToCollect")
                    .addHeader("Content-Type", "application/json")
                    .bodyString(mapper.writeValueAsString(tmp), ContentType.APPLICATION_JSON)
                    .execute();
            tasksRepository.save(tmp);
            return HttpStatus.OK;

        }
    }

    public List<TaskToCollect>  getTaskToCollect(Long id, String name, Timestamp dateStart, Timestamp dateEnd){
        if (id == null &&  name== null && dateStart==null && dateEnd == null) {
            return tasksRepository.findAll();
        }
        if (id != null){
            return List.of(tasksRepository.findById(id).get()) ;
        }
        else{
            List<TaskToCollect> tasksToCollect = tasksRepository.findAll();
            if (name!=null){
                tasksToCollect = tasksToCollect.stream().filter(e->e.getName().equals(name)).toList();
            }
            if (dateStart!=null){
                tasksToCollect = tasksToCollect.stream()
                                .filter(e->e.getDateStart().before(dateStart))
                                .collect(Collectors.toList());
            }
            if (dateEnd!=null){
                tasksToCollect = tasksToCollect.stream()
                                .filter(e->e.getDateEnd().after(dateEnd))
                                .collect(Collectors.toList());
            }
            return tasksToCollect;
        }
    }

    public ResponseEntity<HttpStatus> addTaskToCollect(TaskToCollect taskToCollect){
        taskToCollect.setStatus("created");
        tasksRepository.save(taskToCollect);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> patchTaskToCollect(TaskToCollect taskToCollect) throws IOException {
            TaskToCollect tmp = tasksRepository.findById(taskToCollect.getId()).get();
            if (!tmp.getStatus().equals(taskToCollect.getStatus())) {
                return ResponseEntity.ok(changeTaskStatus(tmp.getId(), taskToCollect.getStatus()));
            }
            tasksRepository.save(taskToCollect);
        return  ResponseEntity.ok(HttpStatus.OK);
    }


    public HttpStatus deleteTaskToCollect(Long id){
        TaskToCollect tmp = tasksRepository.findById(id).get();
        if (!tmp.getStatus().equals("finished")){
            return HttpStatus.BAD_REQUEST;
        }
        else{
            tasksRepository.deleteById(id);
            return HttpStatus.OK;
        }
    }


}
