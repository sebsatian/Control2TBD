<template>
  <div class="register-container">
    <h2 class="text-center mb-4">Registro</h2>
    <form @submit.prevent="register" class="register-form">
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
          placeholder="Mínimo 6 caracteres" 
          required
        >
      </div>
      
      <div class="mb-3">
        <label for="confirmPassword" class="form-label">Confirmar contraseña</label>
        <input 
          type="password" 
          class="form-control" 
          id="confirmPassword" 
          v-model="confirmPassword" 
          placeholder="Repite tu contraseña" 
          required
        >
      </div>
      
      <button type="submit" class="btn btn-primary w-100">Registrarse</button>
    </form>
  </div>
</template>

<script>
import RegisterService from '@/services/register.service';

export default {
  name: 'RegisterForm',
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: ''
    };
  },
  methods: {
    async register() {
      if (this.password !== this.confirmPassword) {
        alert('Las contraseñas no coinciden');
        return;
      }
      try {
        const response = await RegisterService.register(this.username, this.password);
        console.log('Usuario registrado:', response);
        alert('Usuario registrado con éxito');
      } catch (error) {
        console.error('Error al registrar el usuario:', error);
        alert('Error al registrar el usuario');
      }
    }
  }
};
</script>

<style scoped>
.register-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
}

h2 {
  color: #333;
}

.register-form .form-label {
  font-weight: 500;
  color: #555;
}

.register-form .form-control {
  border-radius: 6px;
}

.register-form .form-control:focus {
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