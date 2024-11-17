import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class RegisterService {
  async register(username, password) {
    try {
      const response = await axios.post(
        `${API_URL}/auth/register`,
        {
          username,
          password,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error al registrar el usuario:", error);
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

  async checkToken() {
    try {
      const token = localStorage.getItem("jwtToken");
      const response = await axios.post(
        `${API_URL}/auth/check-token`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      return response.data; // Si el token es válido, devuelve los datos
    } catch (error) {
      console.error("Error al verificar el token:", error);
      throw error;
    }
  }
}

// Función independiente para validar la sesión
async function validateSession() {
  const loginService = new LoginService();
  try {
    await loginService.checkToken(); // Llama al método para validar el token
    return true; // Si el token es válido, continúa
  } catch (error) {
    alert("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.");
    localStorage.removeItem("jwtToken"); // Limpia el token si es inválido
    localStorage.removeItem("userId"); // Limpia el userId si es inválido
    window.location.href = "/"; // Redirige a la página principal
    return false; // Bloquea el acceso
  }
}

export const registerService = new RegisterService();
export const loginService = new LoginService();
export { validateSession };
