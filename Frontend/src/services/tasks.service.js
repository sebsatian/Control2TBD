// src/services/tasks.service.js
import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class TaskService {
  async create(title, description, dueDate) {
    try {
      const userId = localStorage.getItem("userId"); // Obtén el userId del localStorage

      if (!userId) {
        throw new Error("No se encontró el userId en el localStorage");
      }

      const response = await axios.post(
        `${API_URL}/task/create`,
        { title, description, dueDate, userId },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error al crear tarea:", error.response?.data || error.message);
      throw error;
    }
  }
    // Método para obtener tareas filtradas por userId
    async filterTasksByUserId(userId) {
      try {
        if (!userId) {
          throw new Error("No se encontró el userId.");
        }
  
        const response = await axios.get(`${API_URL}/task/filter/userId/${userId}`, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        });
  
        return response.data;
      } catch (error) {
        console.error("Error al filtrar tareas por userId:", error.response?.data || error.message);
        throw error;
      }
    }
  }
  
  export default new TaskService();