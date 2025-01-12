package org.kubsu.tuning.controllers;

import org.kubsu.tuning.domain.entities.TaskToCollect;
import org.kubsu.tuning.services.TaskToCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/v1/taskToCollect")
public class TaskToCollectController {

    private TaskToCollectService taskToCollectService;

    @Autowired
    public TaskToCollectController(TaskToCollectService taskToCollectService) {
        this.taskToCollectService = taskToCollectService;
    }

    @GetMapping("")
    public List<TaskToCollect> getTaskToCollect(@RequestParam(name = "id",required = false) Long id,
                                                @RequestParam(name = "name", required = false) String name,
                                                @RequestParam(name = "dateStart",required = false)Timestamp dateStart,
                                                @RequestParam(name = "dateEnd", required = false) Timestamp dateEnd){

        return taskToCollectService.getTaskToCollect(id, name, dateStart, dateEnd);
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> addTaskToCollect(@RequestBody TaskToCollect taskToCollect){
        return taskToCollectService.addTaskToCollect(taskToCollect);
    }

    @PatchMapping("")
    public ResponseEntity<HttpStatus> patchTaskToCollect(@RequestBody TaskToCollect taskToCollect) throws IOException {
        return taskToCollectService.patchTaskToCollect(taskToCollect);
    }

    @DeleteMapping("")
    public HttpStatus deleteTaskToCollect(Long id){
        return taskToCollectService.deleteTaskToCollect(id);
    }

}
