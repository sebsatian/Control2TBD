package com.example.Control2TBD.persistence.repositories;

import com.example.Control2TBD.persistence.entities.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class TaskRepositoryImp implements TaskRepository{

    @Autowired
    private Sql2o sql2o;

    // DEFAULT ------------------------------------------------------------------------------------
    @Override
    public List<TaskEntity> getAll(){
        String sql = "SELECT * FROM tasks";
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(sql).executeAndFetch(TaskEntity.class);
        }
    }

    @Override
    public TaskEntity getTaskById(Long id) {
        String sql = "SELECT * FROM tasks WHERE id = :id";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(TaskEntity.class);
        }
    }

    @Override
    public void saveTask(TaskEntity task) {
        String sql = "INSERT INTO tasks (title, description, due_date, completed, user_id) " +
                     "VALUES (:title, :description, :dueDate, :completed, :userId)";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("title", task.getTitle())
                    .addParameter("description", task.getDescription())
                    .addParameter("dueDate", task.getDueDate())
                    .addParameter("completed", task.getCompleted())
                    .addParameter("userId", task.getUserId())
                    .executeUpdate();
        }
    }

    @Override
    public void updateTask(TaskEntity task) {
        String sql = "UPDATE tasks SET title = :title, description = :description, due_date = :dueDate, " +
                "completed = :completed WHERE id = :id";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("title", task.getTitle())
                    .addParameter("description", task.getDescription())
                    .addParameter("dueDate", task.getDueDate())
                    .addParameter("completed", task.getCompleted())
                    .addParameter("id", task.getId())
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la tarea con ID: " + task.getId(), e);
        }
    }

    @Override
    public void deleteTask(TaskEntity task) {
        String sql = "DELETE FROM tasks WHERE id = :id";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", task.getId())
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la tarea con ID: " + task.getId(), e);
        }
    }

    // FILTERING ----------------------------------------------------------------------------------
    @Override
    public List<TaskEntity> filterTasksByStatus(Boolean completed) {
        String sql = "SELECT * FROM tasks WHERE completed = :completed";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("completed", completed)
                    .executeAndFetch(TaskEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar tareas por estado: " + completed, e);
        }
    }

    @Override
    public List<TaskEntity> filterTasksByUserId(Long userId) {
        String sql = "SELECT * FROM tasks WHERE user_id = :userId";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeAndFetch(TaskEntity.class);
        } catch (Exception e){
            throw new RuntimeException("Error al filtrar tareas por ID de usuario: "+userId.toString(),e);
        }
    }

    @Override
    public List<TaskEntity> filterTasksByKeyword(String keyword) {
        String sql = "SELECT * FROM tasks WHERE title ILIKE :keyword OR description ILIKE :keyword";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("keyword", "%" + keyword + "%")
                    .executeAndFetch(TaskEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar tareas por palabra clave: " + keyword, e);
        }
    }

    // UPDATE -------------------------------------------------------------------------------------
    public void markTaskAsDone(TaskEntity task){
        String sql = "UPDATE tasks SET completed = 1 WHERE id = :id";
        try (org.sql2o.Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id",task.getId())
                    .executeUpdate();
        } catch (Exception e){
            throw new RuntimeException("Error al actualizar estado de Tarea con ID: "+task.getId(),e);
        }
    }
}
