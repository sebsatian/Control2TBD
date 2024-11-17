<template>
  <div class="container mt-4">
    <h2 class="mb-4">Lista de Tareas</h2>

    <div class="d-flex justify-content-between align-items-center mb-3">
      <div>
        <div class="form-check form-switch d-inline-block me-3">
          <input
            class="form-check-input"
            type="checkbox"
            id="filterByStatus"
            v-model="filterByStatus"
            @change="fetchTasks"
          />
          <label class="form-check-label" for="filterByStatus">Filtrar por estado</label>
        </div>

        <div class="form-check form-switch d-inline-block">
          <input
            class="form-check-input"
            type="checkbox"
            id="showCompleted"
            v-model="showCompleted"
            @change="fetchTasks"
          />
          <label class="form-check-label" for="showCompleted">Completadas</label>
        </div>
      </div>

      <button @click="goToCreateTask" class="btn btn-primary">Crear Tarea</button>
    </div>

    <div class="mb-3">
      <input
        type="text"
        class="form-control"
        placeholder="Buscar..."
        v-model="searchQuery"
        @input="fetchTasks"
      />
    </div>

    <table class="table table-striped">
      <thead>
        <tr>
          <th>Título</th>
          <th>Fecha de Vencimiento</th>
          <th>Estado</th>
          <th class="text-end">Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="task in filteredTasks" :key="task.id">
          <td>{{ task.title }}</td>
          <td>{{ task.dueDate }}</td>
          <td>{{ task.completed ? "Completada" : "Pendiente" }}</td>
          <td class="text-end">
            <button
              v-if="!task.completed"
              @click="markAsCompleted(task.id)"
              class="btn btn-success btn-sm me-2"
            >
              Completar
            </button>
            <button @click="viewTask(task.id)" class="btn btn-primary btn-sm">Ver Tarea</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import TaskService from "@/services/tasks.service";

export default {
  name: "ViewTasks",
  data() {
    return {
      tasks: [],
      filterByStatus: false,
      showCompleted: false,
      searchQuery: "",
    };
  },
  computed: {
    filteredTasks() {
      let tasks = this.tasks;

      if (this.filterByStatus) {
        tasks = tasks.filter((task) => task.completed === this.showCompleted);
      }

      if (this.searchQuery) {
        tasks = tasks.filter((task) =>
          task.title.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      }

      return tasks;
    },
  },
  methods: {
    async fetchTasks() {
      try {
        const tasks = await TaskService.filterTasksByUserId(localStorage.getItem("userId"));
        this.tasks = tasks;
      } catch (error) {
        console.error("Error al obtener las tareas:", error.response?.data || error.message);
      }
    },
    async markAsCompleted(taskId) {
      try {
        await TaskService.markAsCompleted(taskId);
        alert("Tarea completada exitosamente.");
        this.fetchTasks(); // Refresca la lista de tareas
      } catch (error) {
        console.error("Error al completar la tarea:", error.response?.data || error.message);
        alert("Error al completar la tarea.");
      }
    },
    goToCreateTask() {
      this.$router.push("/userpage/tasks/create");
    },
    viewTask(taskId) {
      this.$router.push(`/userpage/tasks/${taskId}`);
    },
  },
  mounted() {
    this.fetchTasks();
  },
};
</script>

<style scoped>
.container {
  max-width: 800px;
}
.table th,
.table td {
  vertical-align: middle;
}
.text-end {
  text-align: right;
}
.btn {
  margin-left: 5px;
}
</style>
