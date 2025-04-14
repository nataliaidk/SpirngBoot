<template>
  <div>
    <h2>Autorzy</h2>

    <form @submit.prevent="saveAuthor">
      <input
        v-model="form.name"
        placeholder="Imię i nazwisko autora"
        required
      />
      <button type="submit">{{ editing ? "Zaktualizuj" : "Dodaj" }}</button>
      <button v-if="editing" type="button" @click="resetForm">Anuluj</button>
    </form>

    <div v-if="error" class="error-message">{{ error }}</div>

    <ul class="authors-list">
      <li v-for="author in authors" :key="author.id">
        {{ author.name }}
        <div>
          <button @click="editAuthor(author)">Edytuj</button>
          <button @click="removeAuthor(author.id)">Usuń</button>
        </div>
      </li>
    </ul>

    <div class="pagination">
      <button @click="prevPage" :disabled="page === 0">Poprzednia</button>
      <span>Strona {{ page + 1 }} z {{ totalPages }}</span>
      <button @click="nextPage" :disabled="page + 1 >= totalPages">
        Następna
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import {
  getAllAuthors,
  addAuthor,
  updateAuthor,
  deleteAuthor,
} from "../services/authors";

const authors = ref([]);
const form = ref({ name: "" });
const editing = ref(false);
const editingId = ref(null);

const page = ref(0);
const totalPages = ref(1);
const pageSize = 5;

const fetchAuthors = async () => {
  const res = await getAllAuthors(page.value, pageSize);
  authors.value = res.data.content;
  totalPages.value = res.data.totalPages;
};

const saveAuthor = async () => {
  if (editing.value) {
    await updateAuthor(editingId.value, { name: form.value.name });
  } else {
    await addAuthor({ name: form.value.name });
  }
  resetForm();
  fetchAuthors();
};

const editAuthor = (author) => {
  form.value.name = author.name;
  editing.value = true;
  editingId.value = author.id;
};

const removeAuthor = async (id) => {
  await deleteAuthor(id);
  //cofnij o strone jesli usunieto ostatni elem
  if (authors.value.length === 1 && page.value > 0) {
    page.value--;
  }
  fetchAuthors();
};

const resetForm = () => {
  form.value.name = "";
  editing.value = false;
  editingId.value = null;
};

const nextPage = () => {
  if (page.value + 1 < totalPages.value) {
    page.value++;
    fetchAuthors();
  }
};

const prevPage = () => {
  if (page.value > 0) {
    page.value--;
    fetchAuthors();
  }
};

onMounted(fetchAuthors);
</script>

<style scoped>
.error-message {
  color: red;
  margin: 10px 0;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.authors-list {
  list-style: none;
  padding: 0;
}

.authors-list li {
  padding: 8px;
  margin: 5px 0;
  border: 1px solid rgb(200, 200, 200);
  border-radius: 4px;
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
