import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class TaskService {
  // Método para crear una nueva tarea
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
      console.error(
        "Error al crear tarea:",
        error.response?.data || error.message
      );
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
      console.error(
        "Error al filtrar tareas por userId:",
        error.response?.data || error.message
      );
      throw error;
    }
  }

  // Método para obtener los detalles de una tarea por ID
  async getTaskDetails(taskId) {
    try {
      const response = await axios.get(`${API_URL}/task/${taskId}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error(
        "Error al obtener los detalles de la tarea:",
        error.response?.data || error.message
      );
      throw error;
    }
  }

  // Método para marcar una tarea como completada
  async markAsCompleted(taskId) {
    try {
      const response = await axios.patch(
        `${API_URL}/task/complete/${taskId}`,
        {},
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );
      return response.data;
    } catch (error) {
      console.error("Error al marcar tarea como completada:", error.response?.data || error.message);
      throw error;
    }
  }

  // Actualizar tarea
  async updateTask(taskId, taskData) {
    await axios.put(`${API_URL}/task/edit/${taskId}`, taskData, {
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
    });
}


async deleteTask(taskId) {
  try {
    await axios.delete(`${API_URL}/task/delete/${taskId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
    });
  } catch (error) {
    console.error("Error al eliminar la tarea:", error.response?.data || error.message);
    throw error;
  }
}


}



export default new TaskService();
