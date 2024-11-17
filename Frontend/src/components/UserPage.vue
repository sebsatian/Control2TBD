<template>
  <div class="user-page-container">
    <h2>Bienvenido.</h2>
    <p>Selecciona una acción:</p>
    <!-- Botones -->
    <button @click="handleButton1Click" class="btn btn-primary">Ver Tareas</button>
    <button @click="handleButton2Click" class="btn btn-primary">Notificaciones</button>
    <button @click="handleLogout" class="btn btn-danger">Cerrar Sesión</button>
  </div>
</template>

<script>
export default {
  name: 'UserPage',
  methods: {
    // Navega a la página de tareas
    handleButton1Click() {
      this.$router.push('/userpage/tasks');
    },
    // Navega a la página de notificaciones
    handleButton2Click() {
      this.$router.push('/userpage/notifications');
    },
    // Cierra sesión y redirige
    handleLogout() {
      // Limpia el token almacenado y cualquier dato de sesión
      localStorage.removeItem('jwtToken'); // Borra el token JWT
      sessionStorage.clear(); // Limpia cualquier otro dato almacenado en la sesión
      
      // Redirige al login
      this.$router.push('/');
    }
  }
};
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
