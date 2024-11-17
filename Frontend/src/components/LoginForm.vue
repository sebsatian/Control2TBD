<template>
  <div class="login-container">
    <h2 class="text-center mb-4">Logueo</h2>
    <form @submit.prevent="login" class="login-form">
      <div class="mb-3">
        <label for="username" class="form-label">Nombre de usuario</label>
        <input 
          type="text" 
          class="form-control" 
          id="username" 
          v-model="username" 
          placeholder="Ingresa tu nombre de usuario" 
          required
        >
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">Contraseña</label>
        <input 
          type="password" 
          class="form-control" 
          id="password" 
          v-model="password" 
          placeholder="Ingresa tu contraseña" 
          required
        >
      </div>

      <button type="submit" class="btn btn-primary w-100">Loguearse</button>
    </form>
  </div>
</template>

<script>
import { loginService } from '@/services/user.service';

export default {
  name: 'LoginForm',
  data() {
    return {
      username: '',
      password: ''
    };
  },
  methods: {
    async login() {
      try {
        // Llamada al servicio de login
        const response = await loginService.login(this.username, this.password);

        // Si la respuesta es exitosa, redirigir al usuario
        console.log('Usuario logueado:', response);
        alert('Usuario logueado con éxito');

        // Redirigir a la página de usuario
        this.$router.push('/userpage'); // Redirige a /userpage (puedes cambiar la ruta según tu estructura)
      } catch (error) {
        console.error('Error al loguear el usuario:', error);
        alert('Error al loguear el usuario');
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

h2 {
  color: #333;
}

.login-form .form-label {
  font-weight: 500;
  color: #555;
}

.login-form .form-control:focus {
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.btn-primary:hover {
  background-color: #0056b3;
  box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
}
</style>