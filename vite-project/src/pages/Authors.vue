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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAllAuthors, addAuthor, updateAuthor, deleteAuthor } from '../services/authors'

const authors = ref([])
const form = ref({ name: '' })
const editing = ref(false)
const editingId = ref(null)

const fetchAuthors = async () => {
  const res = await getAllAuthors()
  authors.value = res.data
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
  fetchAuthors()
}

const resetForm = () => {
  form.value.name = ''
  editing.value = false
  editingId.value = null
}

onMounted(fetchAuthors)
</script>
