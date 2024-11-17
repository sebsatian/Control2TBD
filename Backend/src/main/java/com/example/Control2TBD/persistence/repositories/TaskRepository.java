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

    // FILTERING ----------------------------------------------------------------------------------
    List<TaskEntity> filterByCompleted(Long userId, Boolean completed);

    List<TaskEntity> filterTasksByUserId(Long id);

    List<TaskEntity> filterByKeyword(Long userId, String keyword);

    List<TaskEntity> getTasksDueInAWeek(Long userId);

    List<TaskEntity> filterTasksByBoth(Long userId, Boolean completed, String keyword);

    // UPDATE -------------------------------------------------------------------------------------
    void completeTask(Long taskId);
}
