package com.example.Control2TBD.controllers;

import com.example.Control2TBD.persistence.entities.TaskEntity;
import com.example.Control2TBD.services.TaskService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskEntity> createNewTask(@RequestBody TaskEntity newTask){
        taskService.saveTask(newTask);
        return ResponseEntity.ok(newTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id){
        TaskEntity foundTask = taskService.getTaskById(id);
        return ResponseEntity.ok(foundTask);
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskEntity>> getAll(){
        return ResponseEntity.ok(taskService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskEntity> updateMapping(@PathVariable Long id, @RequestBody TaskEntity newValues){
        //CHECK EXISTENCE, CAN'T UPDATE SOMETHING THAT DOES NOT EXIST
        TaskEntity foundTask = taskService.getTaskById(id);
        if (foundTask == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //IF IT EXISTS, CONTINUE
        taskService.updateTask(newValues);
        return ResponseEntity.ok(newValues);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        //CHECK EXISTENCE, CAN'T UPDATE SOMETHING THAT DOES NOT EXIST
        TaskEntity foundTask = taskService.getTaskById(id);
        if (foundTask == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        taskService.deleteTask(foundTask);
        return ResponseEntity.noContent().build();
    }
}