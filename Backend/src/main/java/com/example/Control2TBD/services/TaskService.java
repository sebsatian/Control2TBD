package com.example.Control2TBD.services;

import com.example.Control2TBD.persistence.entities.TaskEntity;
import com.example.Control2TBD.persistence.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void saveTask(String title, String description, LocalDate dueDate, Long userId) {
        taskRepository.saveTask(title, description, dueDate, userId);
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
    public void completeTask(Long taskId) {
        taskRepository.completeTask(taskId);
    }


}
