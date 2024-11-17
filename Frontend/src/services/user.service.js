// src/services/user.service.js
import axios from 'axios';

const API_URL = process.env.VUE_APP_BACKEND_IP;

class RegisterService {
  async register(username, password) {
    try {
      const response = await axios.post(`${API_URL}/auth/register`, {
        username,
        password
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      return response.data;
    } catch (error) {
      console.error('Error al registrar el usuario:', error);
      throw error; // Esto permitirá manejar el error desde donde se llama este método
    }
  }
}


class LoginService {
  async login(username, password) {
    try {
      const response = await axios.post(`${API_URL}/auth/login`, { username, password });

      // Extraer token y userId desde la respuesta
      const { token, userId } = response.data;

      // Guardar en localStorage
      localStorage.setItem('jwtToken', token);
      localStorage.setItem('userId', userId);

      return { token, userId }; // Retornar los datos si se necesitan en el componente
    } catch (error) {
      console.error('Error al iniciar sesión:', error);
      throw error;
    }
  }
}



export const registerService = new RegisterService();
export const loginService = new LoginService();