<template>
  <form @submit.prevent="addBook">
    <input v-model="title" placeholder="Tytuł" />
    <input v-model="author" placeholder="Autor" />
    <button type="submit">Dodaj książkę</button>
  </form>
</template>

<script>
import api from '../services/api'

export default {
  data() {
    return {
      title: '',
      author: ''
    }
  },
  methods: {
    async addBook() {
      await api.createBook({ title: this.title, author: this.author })
      this.title = ''
      this.author = ''
      this.$emit('book-added')
    }
  }
}
</script>
