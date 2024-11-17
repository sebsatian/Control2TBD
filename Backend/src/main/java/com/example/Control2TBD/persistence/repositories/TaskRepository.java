package com.example.Control2TBD.persistence.repositories;

import com.example.Control2TBD.persistence.entities.TaskEntity;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository {
    // DEFAULT
    List<TaskEntity> getAll();
    TaskEntity getTaskById(Long id);
    void saveTask(String title, String description, LocalDate dueDate, Long userId);
    void updateTask(TaskEntity task);
    void deleteTask(TaskEntity task);

    // FILTER
    List<TaskEntity> filterTasksByStatus(Boolean completed);
    List<TaskEntity> filterTasksByUserId(Long id);
    List<TaskEntity> filterTasksByKeyword(String keyword);

    // UPDATE
    void markTaskAsDone(TaskEntity task);
}
