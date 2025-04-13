<template>
  <div>
    <header>
      <nav>
        <router-link to="/">Strona główna</router-link>
        <router-link to="/books">Książki</router-link>
        <router-link to="/authors">Autorzy</router-link>
      </nav>
    </header>
    <main>
      <router-view/>
    </main>
  </div>
</template>

<style scoped>
nav {
  display: flex;
  gap: 20px;
  background: #ddd;
  padding: 10px;
}
</style>
