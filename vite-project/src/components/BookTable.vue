<template>
  <div>
    <table>
      <tr><th>Tytuł</th><th>Autor</th><th>Strony</th></tr>
      <tr v-for="book in books.content" :key="book.id">
        <td>{{ book.title }}</td>
        <td>{{ book.author }}</td>
        <td>
          <button @click="deleteBook(book.id)">Usuń</button>
        </td>
      </tr>
    </table>
    <div>
      <button @click="$emit('page-changed', books.number - 1)" :disabled="books.first">Poprzednia</button>
      <button @click="$emit('page-changed', books.number + 1)" :disabled="books.last">Następna</button>
    </div>
  </div>
</template>

<script>
import api from '../services/api'

export default {
  props: ['books'],
  methods: {
    async deleteBook(id) {
      await api.deleteBook(id)
      this.$emit('book-deleted')
    }
  }
}
</script>
