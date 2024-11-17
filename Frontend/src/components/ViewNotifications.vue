<template>
  <div class="container mt-4">
    <h2 class="mb-4 text-center">Notificaciones</h2>
    <p class="text-muted text-center">Se notificarán solicitudes a las que les quede menos de una semana.</p>
    <div v-if="notifications.length">
      <!-- Notification List -->
      <div
        v-for="notification in notifications"
        :key="notification.id"
        class="notification-item mb-3 shadow-sm"
      >
        <div class="notification-row">
          <div class="notification-left">
            Vence en {{ notification.daysRemaining }} días
          </div>
          <div class="notification-center">
            <small class="text-muted">Fecha límite: {{ notification.dueDate }}</small>
          </div>
          <div class="notification-right">
            <button @click="viewTask(notification.id)" class="btn btn-primary btn-sm">
              Ver Tarea
            </button>
          </div>
        </div>
      </div>
      <!-- Clear All Button -->
      <button @click="clearNotifications" class="btn btn-danger mt-3 d-block mx-auto">Borrar Todas</button>
    </div>
    <p v-else class="text-muted text-center mt-4">No hay notificaciones disponibles.</p>
  </div>
</template>

<script>
import TaskService from "@/services/tasks.service";

export default {
  name: "ViewNotifications",
  data() {
    return {
      notifications: [],
    };
  },
  methods: {
    async fetchNotifications() {
      try {
        const userId = localStorage.getItem("userId");
        const tasks = await TaskService.getTasksDueInAWeek(userId);

        // Map tasks into notifications
        this.notifications = tasks
          .map((task) => ({
            id: task.id,
            message: `La tarea "${task.title}"`,
            daysRemaining: this.calculateDaysRemaining(task.dueDate),
            dueDate: task.dueDate,
          }))
          .sort((a, b) => a.daysRemaining - b.daysRemaining); // Ordenar por días restantes
      } catch (error) {
        console.error("Error al obtener notificaciones:", error.response?.data || error.message);
      }
    },
    calculateDaysRemaining(dueDate) {
      const today = new Date();
      const dueDateObj = new Date(dueDate);
      const timeDiff = dueDateObj - today;
      return Math.ceil(timeDiff / (1000 * 60 * 60 * 24)); // Días restantes
    },
    clearNotifications() {
      this.notifications = [];
    },
    viewTask(taskId) {
      this.$router.push(`/userpage/tasks/${taskId}`); // Navegar a la vista de la tarea
    },
  },
  mounted() {
    this.fetchNotifications(); // Cargar notificaciones al montar el componente
  },
};
</script>

<style scoped>
.container {
  max-width: 700px;
  margin: 0 auto;
}

h2 {
  color: #333;
}

.notification-item {
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f4f4f4;
  padding: 15px;
  transition: all 0.3s ease;
}

.notification-item:hover {
  background-color: #e8e8e8;
}

.notification-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.notification-left {
  flex: 1;
  font-weight: bold;
  color: #333;
}

.notification-center {
  flex: 1;
  text-align: center;
  font-size: 0.9rem;
  color: #666;
}

.notification-right {
  flex: 1;
  text-align: right;
}

button {
  cursor: pointer;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}

.btn-danger:hover {
  background-color: #b02a37;
}
</style>
