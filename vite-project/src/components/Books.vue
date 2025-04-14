<template>
  <div>
    <h2>Lista książek</h2>

    <form @submit.prevent="handleSaveBook">
      <input v-model="form.title" placeholder="Tytuł książki" required />
      <select v-model="form.authorId" required>
        <option value="">Wybierz autora</option>
        <option v-for="author in authors" :key="author.id" :value="author.id">
          {{ author.name }}
        </option>
      </select>
      <button type="submit">{{ editing ? "Zaktualizuj" : "Dodaj" }}</button>
      <button v-if="editing" type="button" @click="resetForm">Anuluj</button>
    </form>

    <div v-if="error" class="error-message">{{ error }}</div>

    <ul class="books-list">
      <li v-for="book in books" :key="book.id">
        {{ book.title }} ({{ book.author?.name }})
        <div>
          <button @click="editBook(book)">Edytuj</button>
          <button @click="removeBook(book.id)">Usuń</button>
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
  getAllBooks,
  saveBook as apiSaveBook,
  updateBook,
  deleteBook,
} from "../services/books";
import { getAllAuthors } from "../services/authors";

const books = ref([]);
const authors = ref([]);
const form = ref({ title: "", authorId: "" });
const editing = ref(false);
const editingId = ref(null);
const error = ref(null);

const page = ref(0);
const totalPages = ref(1);
const pageSize = 5;

const fetchBooks = async () => {
  try {
    const res = await getAllBooks(page.value, pageSize);
    books.value = res.data.content;
    totalPages.value = res.data.totalPages;
    error.value = null;
  } catch (err) {
    error.value = "Wystąpił błąd podczas ładowania książek";
    console.error(err);
  }
};

const fetchAllPages = async (getFn, targetArray) => {
  let page = 0;
  const size = 20;
  let allItems = [];

  while (true) {
    try {
      const res = await getFn(page, size);
      allItems.push(...res.data.content);

      if (res.data.last) break;
      page++;
    } catch (err) {
      console.error("Błąd podczas ładowania danych:", err);
      break;
    }
  }

  targetArray.value = allItems;
};

const fetchAuthors = async () => {
  await fetchAllPages(getAllAuthors, authors);
};

const handleSaveBook = async () => {
  try {
    if (!form.value.authorId) {
      error.value = "Proszę wybrać autora";
      return;
    }

    const bookData = {
      title: form.value.title,
      authorId: form.value.authorId,
    };

    if (editing.value) {
      await updateBook(editingId.value, bookData);
    } else {
      await apiSaveBook(bookData);
    }

    resetForm();
    await fetchBooks();
    error.value = null;
  } catch (err) {
    error.value =
      err.response?.data?.message ||
      "Wystąpił błąd podczas zapisywania książki";
    console.error(err);
  }
};

const editBook = (book) => {
  form.value.title = book.title;
  form.value.authorId = book.author?.id || "";
  editing.value = true;
  editingId.value = book.id;
};

const removeBook = async (id) => {
  try {
    await deleteBook(id);

    //cofamy strone jesli ostatni element usunięto
    if (books.value.length === 1 && page.value > 0) {
      page.value--;
    }

    await fetchBooks();
    error.value = null;
  } catch (err) {
    error.value = "Wystąpił błąd podczas usuwania książki";
    console.error(err);
  }
};

const resetForm = () => {
  form.value.title = "";
  form.value.authorId = "";
  editing.value = false;
  editingId.value = null;
};

const nextPage = () => {
  if (page.value + 1 < totalPages.value) {
    page.value++;
    fetchBooks();
  }
};

const prevPage = () => {
  if (page.value > 0) {
    page.value--;
    fetchBooks();
  }
};

onMounted(() => {
  fetchBooks();
  fetchAuthors();
});
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

.books-list {
  list-style: none;
  padding: 0;
}

.books-list li {
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
