<template>
    <div class="editTasks-container">
      <h2 class="text-center mb-4">Editar Tarea</h2>
      <form @submit.prevent="updateTask" class="task-form">
        <div class="mb-3">
          <label for="title" class="form-label">Título</label>
          <input
            type="text"
            class="form-control"
            id="title"
            v-model="title"
            placeholder="Editar título de la tarea"
            required
          />
        </div>
  
        <div class="mb-3">
          <label for="description" class="form-label">Descripción</label>
          <textarea
            class="form-control"
            id="description"
            v-model="description"
            placeholder="Editar descripción de la tarea"
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
  
        <button type="submit" class="btn custom-btn w-100">Guardar Cambios</button>
      </form>
    </div>
  </template>
  
  <script>
  import TaskService from "@/services/tasks.service";
  
  export default {
    name: "EditTask",
    props: ["id"],
    data() {
      return {
        title: "",
        description: "",
        dueDate: "",
      };
    },
    async created() {
      try {
        const task = await TaskService.getTaskDetails(this.id);
        this.title = task.title;
        this.description = task.description;
        this.dueDate = task.dueDate;
      } catch (error) {
        console.error("Error al cargar la tarea:", error);
      }
    },
    methods: {
      async updateTask() {
        const today = new Date().toISOString().split("T")[0];
        if (this.dueDate < today) {
          alert("La fecha límite no puede ser anterior a la fecha actual.");
          return;
        }
  
        try {
          await TaskService.updateTask(this.id, {
            title: this.title,
            description: this.description,
            dueDate: this.dueDate,
          });
          alert("Tarea actualizada con éxito.");
          this.$router.push("/userpage/tasks");
        } catch (error) {
          console.error("Error al actualizar la tarea:", error);
          alert("Error al actualizar la tarea.");
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .editTasks-container {
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
  