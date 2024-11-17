<template>
    <div class="createTasks-container">
      <h2 class="text-center mb-4">Crear Tarea</h2>
      <form @submit.prevent="createTasks" class="task-form">
        <div class="mb-3">
          <label for="title" class="form-label">Título</label>
          <input
            type="text"
            class="form-control"
            id="title"
            v-model="title"
            placeholder="Ingresa el título de la tarea"
            required
          />
        </div>
  
        <div class="mb-3">
          <label for="description" class="form-label">Descripción</label>
          <textarea
            class="form-control"
            id="description"
            v-model="description"
            placeholder="Ingrese descripción de la tarea"
            rows="3"
            required
          ></textarea>
        </div>
  
        <div class="mb-3">
          <label for="dueDate" class="form-label">Fecha Límite</label>
          <input
            type="date"
            class="form-control"
            id="dueDate"
            v-model="dueDate"
            required
          />
        </div>
  
        <button type="submit" class="btn custom-btn w-100">Crear Tarea</button>
      </form>
    </div>
  </template>
  
  <script>
  import TaskService from "@/services/tasks.service";
  
  export default {
    name: "TasksForm",
    data() {
      return {
        title: "",
        description: "",
        dueDate: "",
      };
    },
    methods: {
      async createTasks() {
        // Validación de fecha límite
        const today = new Date().toISOString().split("T")[0];
        if (this.dueDate < today) {
          alert("La fecha límite no puede ser anterior a la fecha actual.");
          return;
        }
  
        // Validación de userId en localStorage
        const userId = localStorage.getItem("userId");
        if (!userId) {
          alert("No se encontró el userId en el localStorage. Inicia sesión nuevamente.");
          return;
        }
  
        try {
          // Enviar datos para crear la tarea
          await TaskService.create(
            this.title,
            this.description,
            this.dueDate,
            userId
          );
          alert("Tarea creada con éxito");
          this.$router.push("/userpage/tasks"); // Redirige a la lista de tareas
        } catch (error) {
          console.error("Error al crear la tarea:", error);
          alert("Error al crear la tarea");
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .createTasks-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .custom-btn {
    background-color: #007bff;
    border-color: #007bff;
    color: white;
    transition: background-color 0.3s, transform 0.2s;
  }
  
  .custom-btn:hover {
    background-color: #0056b3;
    transform: translateY(-2px);
  }
  
  .custom-btn:active {
    background-color: #003d80;
    transform: translateY(0);
  }
  </style>
  