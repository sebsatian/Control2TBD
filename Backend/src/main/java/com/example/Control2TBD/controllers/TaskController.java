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
@CrossOrigin("*")
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

    @GetMapping("/filter/completed")
    public ResponseEntity<List<TaskEntity>> filterTasksByCompleted(
            @RequestParam Long userId,
            @RequestParam Boolean completed) {
        if (userId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<TaskEntity> tasks = taskService.filterByCompleted(userId, completed);
        if (tasks == null || tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 si no hay tareas
        }

        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable Long id, @RequestBody TaskEntity updatedTask) {
        TaskEntity existingTask = taskService.getTaskById(id);
        if (existingTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retornar 404 si no se encuentra
        }

        // Asegurar que el ID esté definido
        updatedTask.setId(id);

        try {
            taskService.updateTask(updatedTask);
            return ResponseEntity.ok(updatedTask); // Retornar la tarea actualizada
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    public ResponseEntity<List<TaskEntity>> filterTasksByKeyword(
            @RequestParam Long userId,
            @RequestParam String keyword) {
        if (userId == null || keyword == null || keyword.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<TaskEntity> tasks = taskService.filterByKeyword(userId, keyword);
        if (tasks == null || tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 si no hay tareas
        }

        return ResponseEntity.ok(tasks);
    }


    @GetMapping("/filter/duedate/week")
    public ResponseEntity<List<TaskEntity>> getTasksDueInAWeek(@RequestParam Long userId) {
        try {
            List<TaskEntity> tasks = taskService.getTasksDueInAWeek(userId);
            if (tasks.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content si no hay tareas
            }
            return ResponseEntity.ok(tasks);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request si el userId es inválido
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Error
        }
    }

    @GetMapping("/filter/both")
    public ResponseEntity<List<TaskEntity>> filterTasksByBoth(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "completed", required = false) Boolean completed,
            @RequestParam(value = "keyword", required = false) String keyword) {

        System.out.println("userId: " + userId);
        System.out.println("completed: " + completed);
        System.out.println("keyword: " + keyword);

        // Llamamos al servicio para obtener las tareas filtradas por ambos parámetros
        List<TaskEntity> tasks = taskService.filterTasksByBoth(userId, completed, keyword);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tasks);
    }





    @PatchMapping("/complete/{id}")
    public ResponseEntity<Void> completeTask(@PathVariable Long id) {
        TaskEntity foundTask = taskService.getTaskById(id);
        if (foundTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Si no existe, retornar 404
        }

        try {
            taskService.completeTask(id);
            return ResponseEntity.noContent().build(); // Retornar 204 si se completa correctamente
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // En caso de error, retornar 500
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        System.out.println("Endpoint DELETE llamado para ID: " + id);
        TaskEntity foundTask = taskService.getTaskById(id);
        if (foundTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        taskService.deleteTask(foundTask);
        return ResponseEntity.noContent().build();
    }



}
