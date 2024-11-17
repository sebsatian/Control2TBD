// src/services/register.service.js
import axios from 'axios';

const API_URL = 'http://localhost:8080'; 

class RegisterService {
  async register(username, password) {
    const response = await axios.post(`${API_URL}/auth/register/`, {
      username,
      password
    });
    return response.data;
  }
}


export default new RegisterService();