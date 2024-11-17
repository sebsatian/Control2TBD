package com.example.Control2TBD.controllers;

import com.example.Control2TBD.persistence.entities.TaskEntity;
import com.example.Control2TBD.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<String> createNewTask(@RequestBody Map<String, Object> taskData) {
        try {
            // Validar claves obligatorias
            if (!taskData.containsKey("title") || !taskData.containsKey("description")
                    || !taskData.containsKey("dueDate") || !taskData.containsKey("userId")) {
                return ResponseEntity.badRequest().body("Faltan campos obligatorios en la solicitud.");
            }

            // Extraer valores
            String title = (String) taskData.get("title");
            String description = (String) taskData.get("description");
            String dueDateString = (String) taskData.get("dueDate");
            Long userId = Long.parseLong(taskData.get("userId").toString());

            // Validar campos
            if (title == null || description == null || dueDateString == null || userId == null) {
                return ResponseEntity.badRequest().body("Campos obligatorios no pueden ser nulos.");
            }

            // Parsear la fecha
            LocalDate dueDate = LocalDate.parse(dueDateString);

            // Llamar al servicio
            taskService.saveTask(title, description, dueDate, userId);

            return ResponseEntity.ok("Tarea creada exitosamente.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("El userId debe ser un número válido.");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("La fecha debe estar en formato 'YYYY-MM-DD'.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al procesar la solicitud.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id) {
        TaskEntity foundTask = taskService.getTaskById(id);
        if (foundTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(foundTask);
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskEntity>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/filter/status")
    public ResponseEntity<List<TaskEntity>> filterTasksByStatus(@RequestParam Boolean status) {
        return ResponseEntity.ok(taskService.filterTaskByStatus(status));
    }

    @RequestMapping(value = "/filter/userId/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<TaskEntity>> filterTasksByUserId(@PathVariable Long userId) {
        System.out.println("Request recibida con userId: " + userId);

        if (userId == null) {
            System.out.println("Error: userId es nulo.");
            return ResponseEntity.badRequest().body(null);
        }

        List<TaskEntity> tasks = taskService.filterTaskByUserId(userId);

        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No se encontraron tareas para el userId: " + userId);
            return ResponseEntity.noContent().build(); // HTTP 204 si no hay tareas
        }

        System.out.println("Tareas encontradas: " + tasks.size());
        return ResponseEntity.ok(tasks);
    }



    @GetMapping("/filter/keyword")
    public ResponseEntity<List<TaskEntity>> filterTasksByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(taskService.filterTaskByKeyword(keyword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskEntity> updateMapping(@PathVariable Long id, @RequestBody TaskEntity newValues) {
        TaskEntity foundTask = taskService.getTaskById(id);
        if (foundTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        taskService.updateTask(newValues);
        return ResponseEntity.ok(newValues);
    }

    @PutMapping("/markAsDone/{id}")
    public ResponseEntity<TaskEntity> markTaskAsDone(@PathVariable Long id) {
        TaskEntity foundTask = taskService.getTaskById(id);
        if (foundTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        taskService.markTaskAsDone(id);
        return ResponseEntity.ok(foundTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        TaskEntity foundTask = taskService.getTaskById(id);
        if (foundTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        taskService.deleteTask(foundTask);
        return ResponseEntity.noContent().build();
    }
}
