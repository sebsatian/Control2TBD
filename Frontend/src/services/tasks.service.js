import axios from "axios";

const API_URL = process.env.VUE_APP_BACKEND_IP;

class TaskService {
  // Método para crear una nueva tarea
  async create(title, description, dueDate) {
    try {
      const userId = localStorage.getItem("userId");

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

  // Método para obtener todas las tareas por userId
  async filterTasksByUserId(userId) {
    try {
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

  // Método para filtrar tareas por estado
  async filterTasksByCompleted(userId, completed) {
    try {
      const response = await axios.get(`${API_URL}/task/filter/completed`, {
        params: { userId, completed },
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error al filtrar tareas por estado:", error.response?.data || error.message);
      throw error;
    }
  }

  // Método para filtrar tareas por palabra clave
  async filterTasksByKeyword(userId, keyword) {
    try {
      const response = await axios.get(`${API_URL}/task/filter/keyword`, {
        params: { userId, keyword },
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error al filtrar tareas por palabra clave:", error.response?.data || error.message);
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
      console.error("Error al obtener los detalles de la tarea:", error.response?.data || error.message);
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
    try {
      await axios.put(`${API_URL}/task/edit/${taskId}`, taskData, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
    } catch (error) {
      console.error("Error al actualizar tarea:", error.response?.data || error.message);
      throw error;
    }
  }

  // Eliminar tarea
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

// Método para obtener tareas con vencimiento en menos de una semana
async getTasksDueInAWeek(userId) {
  try {
    const response = await axios.get(`${API_URL}/task/filter/duedate/week`, {
      params: { userId },
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error al obtener tareas con vencimiento próximo:", error.response?.data || error.message);
    throw error;
  }
}

async filterTasksByBoth(params) {
  try {
    const response = await axios.get(`${API_URL}/task/filter/both`, {
      params, // userId, completed, keyword
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error al filtrar tareas por ambos criterios:", error.response?.data || error.message);
    throw error;
  }
}


}



export default new TaskService();
