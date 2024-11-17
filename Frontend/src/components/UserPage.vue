<template>
    <div class="user-page-container">
      
      <h2>Bienvenido.</h2>
      <p>Selecciona una acción:</p>
      <!-- Botones -->
      <button @click="handleButton1Click" class="btn btn-primary">Ver Tareas</button>
      <button @click="handleButton2Click" class="btn btn-primary">Notificaciones</button>
      <button @click="handleButton3Click" class="btn btn-danger">Cerrar Sesión</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'UserPage',
    methods: {
      // Acción del primer botón
      handleButton1Click() {
        this.$router.push('/userpage/tasks'); // Navega a la página de tareas
      },
      // Acción del segundo botón
      handleButton2Click() {
        this.$router.push('/userpage/notifications'); // Navegar a la página de notificaciones
      },
      // Acción del tercer botón
      handleButton3Click() {
        // Aquí podrías agregar la lógica de cerrar sesión (limpiar tokens, redirigir, etc.)
        this.$router.push('/'); // Redirige al login después de cerrar sesión
      }
    }
  }
  </script>
  
  <style scoped>
  .user-page-container {
    text-align: center;
    padding: 40px;
  }
  
  h2 {
    color: #333;
  }
  
  button {
    margin: 10px;
    padding: 10px 20px;
    font-size: 1rem;
    cursor: pointer;
  }
  
  .btn-primary {
    background-color: #007bff;
    border-color: #007bff;
  }

  .btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}
  
  button:hover {
    opacity: 0.8;
  }

  </style>
  