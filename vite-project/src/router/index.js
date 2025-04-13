import { createRouter, createWebHistory } from "vue-router";
import MainLayout from "../layouts/MainLayout.vue";
import Home from "../pages/Home.vue";
import Books from "../pages/Books.vue";
import Authors from "../pages/Authors.vue";
import Readers from "../pages/Readers.vue";
import Rentals from "../pages/Rentals.vue";

const routes = [
  {
    path: "/",
    component: MainLayout,
    children: [
      { path: "/", component: Home },
      { path: "/books", component: Books },
      { path: "/authors", component: Authors },
      { path: "/readers", component: Readers },
      { path: "/rentals", component: Rentals },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
