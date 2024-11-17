<template>
  <div class="task-details-container">
    <h2>Detalles de la Tarea</h2>
    <div class="actions mb-3">
      <button @click="editTask" class="btn btn-primary me-3">Editar Tarea</button>
      <button @click="deleteTask" class="btn btn-danger">Eliminar Tarea</button>
    </div>
    <div v-if="task">
      <table class="table table-bordered">
        <tbody>
          <tr>
            <th>ID</th>
            <td>{{ task.id }}</td>
          </tr>
          <tr>
            <th>Título</th>
            <td>{{ task.title }}</td>
          </tr>
          <tr>
            <th>Descripción</th>
            <td>{{ task.description }}</td>
          </tr>
          <tr>
            <th>Fecha de Vencimiento</th>
            <td>{{ task.dueDate }}</td>
          </tr>
          <tr>
            <th>Estado</th>
            <td>{{ task.completed ? "Completada" : "Pendiente" }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-else>
      <p>Cargando detalles de la tarea...</p>
    </div>
  </div>
</template>

<script>
import TaskService from "@/services/tasks.service";

export default {
  name: "TaskDetails",
  props: ["id"],
  data() {
    return {
      task: null,
    };
  },
  async created() {
    try {
      this.task = await TaskService.getTaskDetails(this.id);
    } catch (error) {
      console.error("Error al obtener los detalles de la tarea:", error);
    }
  },
  methods: {
    editTask() {
      // Redirige al componente EditTask pasando el ID de la tarea
      this.$router.push(`/userpage/tasks/${this.task.id}/edit`);
    },
    async deleteTask() {
      try {
        if (confirm("¿Estás seguro de que deseas eliminar esta tarea?")) {
          await TaskService.deleteTask(this.task.id);
          alert("Tarea eliminada con éxito.");
          this.$router.push("/userpage/tasks");
        }
      } catch (error) {
        console.error("Error al eliminar la tarea:", error);
        alert("Error al eliminar la tarea.");
      }
    },
  },
};
</script>

<style scoped>
.task-details-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

.actions {
  text-align: right;
}

.actions .btn {
  margin-right: 10px;
}

.actions .btn:last-child {
  margin-right: 0;
}

h2 {
  color: #333;
}

.table {
  margin-top: 20px;
}

th {
  width: 40%;
  background-color: #f1f1f1;
}

td {
  width: 60%;
}
</style>
