<template>
  <div>
    <h2>📚 Lista książek</h2>

    <form @submit.prevent="handleSaveBook">
      <input v-model="form.title" placeholder="Tytuł książki" required />
      <select v-model="form.authorId" required>
        <option value="">Wybierz autora</option>
        <option v-for="author in authors" :key="author.id" :value="author.id">
          {{ author.name }}
        </option>
      </select>
      <button type="submit">{{ editing ? "Zaktualizuj" : "Dodaj" }}</button>
    </form>

    <div v-if="error" class="error-message">{{ error }}</div>

    <ul>
      <li v-for="book in books" :key="book.id">
        {{ book.title }} ({{ book.author?.name }})
        <button @click="editBook(book)">✏️</button>
        <button @click="removeBook(book.id)">🗑️</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAllBooks, saveBook as apiSaveBook, updateBook, deleteBook } from '../services/books'
import { getAllAuthors } from '../services/authors'

const books = ref([])
const authors = ref([])
const form = ref({ title: '', authorId: '' })
const editing = ref(false)
const editingId = ref(null)
const error = ref(null)

const fetchBooks = async () => {
  try {
    const res = await getAllBooks()
    books.value = res.data
    error.value = null
  } catch (err) {
    error.value = 'Wystąpił błąd podczas ładowania książek'
    console.error(err)
  }
}

const fetchAuthors = async () => {
  try {
    const res = await getAllAuthors()
    authors.value = res.data
    error.value = null
  } catch (err) {
    error.value = 'Wystąpił błąd podczas ładowania autorów'
    console.error(err)
  }
}

const handleSaveBook = async () => {
  try {
    if (!form.value.authorId) {
      error.value = 'Proszę wybrać autora'
      return
    }

    const bookData = {
      title: form.value.title,
      authorId: form.value.authorId // Zmieniamy strukturę zgodnie z wymaganiami backendu
    }

    if (editing.value) {
      await updateBook(editingId.value, bookData)
    } else {
      await apiSaveBook(bookData)
    }
    
    resetForm()
    await fetchBooks()
    error.value = null
  } catch (err) {
    error.value = err.response?.data?.message || 'Wystąpił błąd podczas zapisywania książki'
    console.error(err)
  }
}

const editBook = (book) => {
  form.value.title = book.title
  form.value.authorId = book.author?.id || ''
  editing.value = true
  editingId.value = book.id
}

const removeBook = async (id) => {
  try {
    await deleteBook(id)
    await fetchBooks()
    error.value = null
  } catch (err) {
    error.value = 'Wystąpił błąd podczas usuwania książki'
    console.error(err)
  }
}

const resetForm = () => {
  form.value.title = ''
  form.value.authorId = ''
  editing.value = false
  editingId.value = null
}

onMounted(() => {
  fetchBooks()
  fetchAuthors()
})
</script>

<style scoped>
.error-message {
  color: red;
  margin: 10px 0;
}
</style>