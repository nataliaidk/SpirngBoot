<template>
  <div>
    <h2>Autorzy</h2>

    <form @submit.prevent="saveAuthor">
      <input v-model="form.name" placeholder="Imię i nazwisko autora" required />
      <button type="submit">{{ editing ? "Zaktualizuj" : "Dodaj" }}</button>
    </form>

    <ul>
      <li v-for="author in authors" :key="author.id">
        {{ author.name }}
        <button @click="editAuthor(author)">✏️</button>
        <button @click="removeAuthor(author.id)">🗑️</button>
      </li>
    </ul>

    <!-- Nawigacja paginacji -->
    <div style="margin-top: 1em">
      <button @click="prevPage" :disabled="page === 0">⬅️ Poprzednia</button>
      <span>Strona {{ page + 1 }} z {{ totalPages }}</span>
      <button @click="nextPage" :disabled="page+ 1 >= totalPages">➡️ Następna</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAllAuthors, addAuthor, updateAuthor, deleteAuthor } from '../services/authors'

const authors = ref([])
const form = ref({ name: '' })
const editing = ref(false)
const editingId = ref(null)

const page = ref(0)
const totalPages = ref(1)
const pageSize = 5

const fetchAuthors = async () => {
  const res = await getAllAuthors(page.value, pageSize)
  authors.value = res.data.content
  totalPages.value = res.data.totalPages
}

const saveAuthor = async () => {
  if (editing.value) {
    await updateAuthor(editingId.value, { name: form.value.name })
  } else {
    await addAuthor({ name: form.value.name })
  }
  resetForm()
  fetchAuthors()
}

const editAuthor = (author) => {
  form.value.name = author.name
  editing.value = true
  editingId.value = author.id
}

const removeAuthor = async (id) => {
  await deleteAuthor(id)
  // Jeżeli usunięto ostatni element z ostatniej strony, cofnij o stronę
  if (authors.value.length === 1 && page.value > 0) {
    page.value--
  }
  fetchAuthors()
}

const resetForm = () => {
  form.value.name = ''
  editing.value = false
  editingId.value = null
}

const nextPage = () => {
  if (page.value + 1 < totalPages.value) {
    page.value++
    fetchAuthors()
  }
}

const prevPage = () => {
  if (page.value > 0) {
    page.value--
    fetchAuthors()
  }
}

onMounted(fetchAuthors)
</script>
