<template>
  <div>
    <nav>
      <router-link to="/">🏠 Home</router-link> |
      <router-link to="/books">📚 Książki</router-link>
      <router-link to="/authors">👤 Autorzy</router-link>
      <router-link to="/readers">Czytelnicy</router-link>
      <router-link to="/rentals">Wypożyczenia</router-link>
    </nav>
    <router-view />
  </div>
</template>

<style scoped>
nav {
  padding: 20px;
  background: #f0f0f0;
}
a {
  margin-right: 10px;
  text-decoration: none;
}
</style>
<script setup>
// NIE importuj tutaj HelloWorld, jeśli nie używasz
</script>