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
    // Crear el objeto de datos a enviar
    const data = { username, password };

    // Imprimir el JSON antes de enviarlo
    console.log("Datos enviados al backend:", JSON.stringify(data, null, 2));

    try {
      // Realizar la solicitud al backend
      const response = await axios.post(`${API_URL}/auth/login`, data);

      // Extraer token y userId desde la respuesta
      const { token, userId } = response.data;

      // Guardar en localStorage
      localStorage.setItem("jwtToken", token);
      localStorage.setItem("userId", userId);

      // Retornar los datos si se necesitan en el componente
      return { token, userId };
    } catch (error) {
      console.error("Error al iniciar sesión:", error);
      throw error;
    }
  }
}




export const registerService = new RegisterService();
export const loginService = new LoginService();