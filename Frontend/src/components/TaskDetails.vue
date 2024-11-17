<template>
    <div class="task-details-container">
      <h2>Detalles de la Tarea</h2>
      <div v-if="task">
        <p><strong>ID:</strong> {{ task.id }}</p>
        <p><strong>Nombre:</strong> {{ task.name }}</p>
        <p><strong>Descripción:</strong> {{ task.description }}</p>
        <p><strong>Fecha de Vencimiento:</strong> {{ task.dueDate }}</p>
        <!-- Otros detalles de la tarea pueden ir aquí -->
      </div>
      <div v-else>
        <p>Cargando detalles de la tarea...</p>
      </div>
    </div>
  </template>
  
  <script>
  // Importa un servicio para obtener los detalles de la tarea (ejemplo)
  import { taskService } from '@/services/task.service';
  
  export default {
    name: 'TaskDetails',
    props: ['taskId'], // Puede ser un ID o un nombre
    data() {
      return {
        task: null, // Los detalles de la tarea
      };
    },
    async created() {
      // Llamar al servicio para obtener los detalles de la tarea usando el taskId
      try {
        const response = await taskService.getTaskDetails(this.taskId);
        this.task = response.data; // Suponiendo que la respuesta contiene la tarea
      } catch (error) {
        console.error('Error al obtener los detalles de la tarea:', error);
      }
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
  
  h2 {
    color: #333;
  }
  
  p {
    font-size: 1rem;
    margin-bottom: 10px;
  }
  
  strong {
    font-weight: 600;
  }
  </style>
  