import { createRouter, createWebHistory } from "vue-router";
import MainLayout from "../layouts/MainLayout.vue";
import Home from "../components/Home.vue";
import Books from "../components/Books.vue";
import Authors from "../components/Authors.vue";
import Readers from "../components/Readers.vue";
import Rentals from "../components/Rentals.vue";

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
