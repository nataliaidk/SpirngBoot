<template>
  <div class="layout">
    <header>
      <nav>
        <router-link to="/">Strona główna</router-link>
        <router-link to="/books">Książki</router-link>
        <router-link to="/authors">Autorzy</router-link>
        <router-link to="/readers">Czytelnicy</router-link>
        <router-link to="/rentals">Wypożyczenia</router-link>
      </nav>
    </header>
    <main>
      <router-view />
    </main>
    <app-footer />
  </div>
</template>

<script setup>
import AppFooter from "../components/AppFooter.vue";
</script>

<style scoped>
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

main {
  max-width: 1280px;
  margin: 0 auto;
}

nav {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  padding: 20px;
  background: #f0f0f0;
  color: #000;
  z-index: 1000;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

a {
  margin-right: 10px;
  text-decoration: none;
  color: #333;
  margin: 0 1rem;
}

a.router-link-active {
  font-weight: bold;
  color: black;
}

main {
  flex: 1;
  padding: 20px;
  margin-top: 80px; /* accounts for fixed nav */
}

footer {
  width: 100%;
}
</style>
