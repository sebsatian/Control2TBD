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



    public void deleteTask(TaskEntity task){
        taskRepository.deleteTask(task);
    }

    // FILTER

    public List<TaskEntity> filterTaskByUserId(Long userId){
        return taskRepository.filterTasksByUserId(userId);
    }

    public List<TaskEntity> filterByCompleted(Long userId, Boolean completed) {
        if (userId == null || completed == null) {
            throw new IllegalArgumentException("userId y completed no pueden ser nulos.");
        }
        return taskRepository.filterByCompleted(userId, completed);
    }
    public List<TaskEntity> filterByKeyword(Long userId, String keyword) {
        if (userId == null || keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("userId y keyword no pueden ser nulos o vacíos.");
        }
        return taskRepository.filterByKeyword(userId, keyword.trim());
    }

    public List<TaskEntity> getTasksDueInAWeek(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("El userId no puede ser nulo");
        }
        return taskRepository.getTasksDueInAWeek(userId);
    }
    public List<TaskEntity> filterTasksByBoth(Long userId, Boolean completed, String keyword) {
        if (userId == null) {
            throw new IllegalArgumentException("El userId no puede ser nulo");
        }
        // Llamar al repositorio para filtrar
        return taskRepository.filterTasksByBoth(userId, completed, keyword);
    }





    // UPDATE
    public void completeTask(Long taskId) {
        taskRepository.completeTask(taskId);
    }
    public void updateTask(TaskEntity task){
        taskRepository.updateTask(task);
    }

}
