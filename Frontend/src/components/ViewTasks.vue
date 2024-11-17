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
          <th>ID</th>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>Estado</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="task in filteredTasks" :key="task.id">
          <td>{{ task.id }}</td>
          <td>{{ task.name }}</td>
          <td>{{ task.description }}</td>
          <td>{{ task.completed ? "Completada" : "Pendiente" }}</td>
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
        tasks = tasks.filter((task) => task.completed);
      }

      if (this.searchQuery) {
        tasks = tasks.filter(
          (task) =>
            task.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
            task.description
              .toLowerCase()
              .includes(this.searchQuery.toLowerCase())
        );
      }

      return tasks;
    },
  },
  methods: {
    async fetchTasks() {
      try {
        const tasks = await TaskService.filterTasksByUserId(
          localStorage.getItem("userId")
        );
        this.tasks = tasks;
      } catch (error) {
        console.error("Error al obtener las tareas:", error.response?.data || error.message);
      }
    },
    goToCreateTask() {
      this.$router.push("/userpage/tasks/create");
    },
  },
  mounted() {
    const userId = localStorage.getItem("userId");
    if (userId) {
      this.fetchTasks();
    } else {
      console.error("No se encontró el userId en el localStorage");
    }
  },
};
</script>

<style scoped>
.container {
  max-width: 800px;
}
</style>