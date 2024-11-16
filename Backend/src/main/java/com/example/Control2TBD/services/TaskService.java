package com.example.Control2TBD.services;

import com.example.Control2TBD.persistence.entities.TaskEntity;
import com.example.Control2TBD.persistence.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    // DEFAULT
    public List<TaskEntity> getAll(){
        return taskRepository.getAll();
    }

    public TaskEntity getTaskById(Long id){
        return taskRepository.getTaskById(id);
    }

    public void saveTask(TaskEntity task){
        taskRepository.saveTask(task);
    }

    public void updateTask(TaskEntity task){
        taskRepository.updateTask(task);
    }

    public void deleteTask(TaskEntity task){
        taskRepository.deleteTask(task);
    }

    // FILTER
    public List<TaskEntity> filterTaskByStatus(Boolean status){
        return taskRepository.filterTasksByStatus(status);
    }

    public List<TaskEntity> filterTaskByUserId(Long userId){
        return taskRepository.filterTasksByUserId(userId);
    }

    public List<TaskEntity> filterTaskByKeyword(String keyword){
        return taskRepository.filterTasksByKeyword(keyword);
    }

    // UPDATE
    public void markTaskAsDone(TaskEntity task){
        taskRepository.markTaskAsDone(task);
    }

    public void markTaskAsDone(Long id){
        TaskEntity foundTask = getTaskById(id);
        if (!(foundTask == null)){
            markTaskAsDone(foundTask);
        }
    }
}
