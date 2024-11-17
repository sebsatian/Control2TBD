<template>
    <div>
      <h2>Notificaciones</h2>
      <div v-if="notifications.length">
        <!-- Notification List -->
        <div
          v-for="notification in notifications"
          :key="notification.id"
          :class="{ 'notification-item': true, unread: !notification.read }"
        >
          <p>{{ notification.message }}</p>
          <button @click="toggleRead(notification.id)">
            {{ notification.read ? "Marcar como leido" : "Marcar como no leido" }}
          </button>
        </div>
        <!-- Clear All Button -->
        <button @click="clearNotifications" class="clear-button">Clear All</button>
      </div>
      <p v-else>No notifications available.</p>
    </div>
  </template>
  
  <script>
  export default {
    name: "ViewNotifications",
    data() {
      return {
        notifications: [
          { id: 1, message: "Welcome to our app!", read: false },
          { id: 2, message: "Your profile has been updated.", read: true },
          { id: 3, message: "You have a new message.", read: false },
        ],
      };
    },
    methods: {
      toggleRead(id) {
        const notification = this.notifications.find((n) => n.id === id);
        if (notification) {
          notification.read = !notification.read;
        }
      },
      clearNotifications() {
        this.notifications = [];
      },
    },
  };
  </script>
  
  <style scoped>
  h2 {
    margin-bottom: 20px;
  }
  
  .notification-item {
    border: 1px solid #ccc;
    padding: 10px;
    margin: 10px 0;
    border-radius: 5px;
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .notification-item.unread {
    background-color: #f8f9fa;
    font-weight: bold;
  }
  
  button {
    padding: 5px 10px;
    cursor: pointer;
    border: none;
    border-radius: 3px;
    background-color: #007bff;
    color: #fff;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  .clear-button {
    margin-top: 20px;
    background-color: #dc3545;
  }
  
  .clear-button:hover {
    background-color: #b02a37;
  }
  </style>
  