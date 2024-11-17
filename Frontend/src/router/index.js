import { createRouter, createWebHistory } from 'vue-router';
import MainPage from '../components/MainPage.vue';
import RegisterForm from '../components/RegisterForm.vue';
import LoginForm from '../components/LoginForm.vue';
import UserPage from '../components/UserPage.vue';
import ViewTasks from '../components/ViewTasks.vue';
import ViewNotifications from '../components/ViewNotifications.vue';
import CreateTasks from '../components/CreateTasks.vue';
import TaskDetails from '../components/TaskDetails.vue'; // Importa el componente de detalles de tarea

const routes = [
  {
    path: '/',
    name: 'MainPage',
    component: MainPage,
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterForm,
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginForm,
  },
  {
    path: '/userpage',
    name: 'UserPage',
    component: UserPage,
  },
  {
    path: '/userpage/tasks',
    name: 'ViewTasks',
    component: ViewTasks,
  },
  {
    path: '/userpage/tasks/create',
    name: 'CreateTask',
    component: CreateTasks,
  },
  {
    path: '/userpage/notifications',
    name: 'ViewNotifications',
    component: ViewNotifications,
  },
  {
    path: '/userpage/tasks/:id', // Ruta para los detalles de una tarea
    name: 'TaskDetails',
    component: TaskDetails,
    props: true, // Pasa el parámetro `id` como prop al componente
  },
];

const router = createRouter({
  history: createWebHistory('/'),
  routes,
});

export default router;
