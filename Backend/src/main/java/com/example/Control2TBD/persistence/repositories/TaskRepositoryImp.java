package com.example.Control2TBD.persistence.repositories;

import com.example.Control2TBD.persistence.entities.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.time.LocalDate;
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
        // Consulta SQL corregida
        String sql = "SELECT id, title, description, due_date AS dueDate, completed, user_id AS userId FROM tasks WHERE id = :id";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(TaskEntity.class); // Devuelve la primera coincidencia
        }
    }



    @Override
    public void saveTask(String title, String description, LocalDate dueDate, Long userId) {
        String sql = "INSERT INTO tasks (title, description, due_date, completed, user_id) " +
                "VALUES (:title, :description, :dueDate, :completed, :userId)";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("title", title)
                    .addParameter("description", description)
                    .addParameter("dueDate", dueDate)
                    .addParameter("completed", false) // Las tareas nuevas no están completadas por defecto
                    .addParameter("userId", userId)
                    .executeUpdate();
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
    public List<TaskEntity> filterTasksByUserId(Long userId) {
        String sql = "SELECT id, title, description, due_date AS dueDate, completed, user_id AS userId FROM tasks WHERE user_id = :userId";
        try (Connection con = sql2o.open()) {
            System.out.println("Filtrando tareas por userId: " + userId);
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeAndFetch(TaskEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar tareas por ID de usuario: " + userId, e);
        }
    }

    @Override
    public List<TaskEntity> filterByCompleted(Long userId, Boolean completed) {
        String sql = "SELECT id, title, description, due_date AS dueDate, completed, user_id AS userId " +
                "FROM tasks WHERE user_id = :userId AND completed = :completed";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .addParameter("completed", completed)
                    .executeAndFetch(TaskEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar tareas por estado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<TaskEntity> filterByKeyword(Long userId, String keyword) {
        String sql = "SELECT id, title, description, due_date AS dueDate, completed, user_id AS userId " +
                "FROM tasks WHERE user_id = :userId AND title ILIKE :keyword";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .addParameter("keyword", "%" + keyword + "%")
                    .executeAndFetch(TaskEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar tareas por palabra clave: " + e.getMessage(), e);
        }
    }

    @Override
    public List<TaskEntity> getTasksDueInAWeek(Long userId) {
        String sql = "SELECT id, title, description, due_date AS dueDate, completed, user_id AS userId " +
                "FROM tasks " +
                "WHERE user_id = :userId AND due_date <= CURRENT_DATE + INTERVAL '7 days'";

        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeAndFetch(TaskEntity.class);
        }
    }

    @Override
    public List<TaskEntity> filterTasksByBoth(Long userId, Boolean completed, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT id, title, description, completed, due_date AS dueDate, user_id AS userId FROM tasks WHERE user_id = :userId");

        if (completed != null) {
            sql.append(" AND completed = :completed");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND title ILIKE :keyword");
        }

        try (Connection con = sql2o.open()) {
            var query = con.createQuery(sql.toString())
                    .addParameter("userId", userId);

            if (completed != null) {
                query.addParameter("completed", completed);
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.addParameter("keyword", "%" + keyword + "%");
            }

            return query.executeAndFetch(TaskEntity.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al filtrar tareas por ambos criterios", e);
        }
    }


    // UPDATE -------------------------------------------------------------------------------------
    @Override
    public void completeTask(Long taskId) {
        String sql = "UPDATE tasks SET completed = true WHERE id = :id";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", taskId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al marcar como completada la tarea con ID: " + taskId, e);
        }
    }
    @Override
    public void updateTask(TaskEntity task) {
        String sql = "UPDATE tasks SET title = :title, description = :description, due_date = :dueDate " +
                "WHERE id = :id";
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("title", task.getTitle())
                    .addParameter("description", task.getDescription())
                    .addParameter("dueDate", task.getDueDate())
                    .addParameter("id", task.getId())
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la tarea con ID: " + task.getId(), e);
        }
    }

}
