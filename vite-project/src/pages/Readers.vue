<template>
  <div>
    <h2>👥 Czytelnicy</h2>

    <form @submit.prevent="handleSaveReader">
      <input v-model="form.name" placeholder="Imię i nazwisko czytelnika" required />
      <button type="submit">{{ editing ? "Zaktualizuj" : "Dodaj" }}</button>
      <button v-if="editing" type="button" @click="resetForm">Anuluj</button>
    </form>

    <div v-if="error" class="error-message">{{ error }}</div>

    <ul class="readers-list">
      <li v-for="reader in readers" :key="reader.id">
        {{ reader.name }}
        <button @click="editReader(reader)">✏️</button>
        <button @click="removeReader(reader.id)">🗑️</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { 
  getAllReaders, 
  addReader as apiAddReader, 
  updateReader, 
  deleteReader 
} from '../services/readers'

const readers = ref([])
const form = ref({ name: '' })
const editing = ref(false)
const editingId = ref(null)
const error = ref(null)

const fetchReaders = async () => {
  try {
    const res = await getAllReaders()
    readers.value = res.data
    error.value = null
  } catch (err) {
    error.value = 'Wystąpił błąd podczas ładowania czytelników'
    console.error(err)
  }
}

const handleSaveReader = async () => {
  try {
    if (!form.value.name.trim()) {
      error.value = 'Nazwa czytelnika nie może być pusta'
      return
    }

    if (editing.value) {
      await updateReader(editingId.value, { name: form.value.name })
    } else {
      await apiAddReader(form.value.name)
    }
    
    resetForm()
    await fetchReaders()
    error.value = null
  } catch (err) {
    error.value = err.response?.data?.message || 'Wystąpił błąd podczas zapisywania czytelnika'
    console.error(err)
  }
}

const editReader = (reader) => {
  form.value.name = reader.name
  editing.value = true
  editingId.value = reader.id
}

const removeReader = async (id) => {
  try {
    if (confirm('Czy na pewno chcesz usunąć tego czytelnika?')) {
      await deleteReader(id)
      await fetchReaders()
      error.value = null
    }
  } catch (err) {
    error.value = 'Wystąpił błąd podczas usuwania czytelnika'
    console.error(err)
  }
}

const resetForm = () => {
  form.value.name = ''
  editing.value = false
  editingId.value = null
}

onMounted(fetchReaders)
</script>

<style scoped>
.error-message {
  color: red;
  margin: 10px 0;
}

.readers-list {
  list-style: none;
  padding: 0;
}

.readers-list li {
  padding: 8px;
  margin: 5px 0;
  border-radius: 4px;
  border-color: rgb(200,200,200);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

button {
  margin-left: 5px;
  cursor: pointer;
}

input {
  padding: 8px;
  margin-right: 10px;
  width: 300px;
}
</style>