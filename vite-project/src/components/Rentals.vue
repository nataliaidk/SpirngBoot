<template>
  <div>
    <h2>Wypożyczenia</h2>

    <form @submit.prevent="handleSaveRental">
      <div class="form-group">
        <select v-model="form.readerId" required>
          <option value="">Wybierz czytelnika</option>
          <option v-for="reader in readers" :key="reader.id" :value="reader.id">
            {{ reader.name }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <select v-model="form.bookId" required>
          <option value="">Wybierz książkę</option>
          <option v-for="book in books" :key="book.id" :value="book.id">
            {{ book.title }} ({{ book.author?.name }})
          </option>
        </select>
      </div>

      <div class="form-group date-row">
        <div class="date-field">
          <label>Data wypożyczenia:</label>
          <input type="date" v-model="form.rentDate" required />
        </div>
        <div class="date-field">
          <label>Data zwrotu:</label>
          <input type="date" v-model="form.returnDate" />
        </div>
      </div>

      <div class="form-actions">
        <button type="submit">{{ editing ? "Zaktualizuj" : "Dodaj" }}</button>
        <button v-if="editing" type="button" @click="resetForm">Anuluj</button>
      </div>
    </form>

    <div v-if="error" class="error-message">{{ error }}</div>

    <table class="rentals-table">
      <thead>
        <tr>
          <th>Czytelnik</th>
          <th>Książka</th>
          <th>Data wypożyczenia</th>
          <th>Data zwrotu</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="rental in rentals" :key="rental.id">
          <td>{{ rental.reader?.name }}</td>
          <td>{{ rental.book?.title }}</td>
          <td>{{ formatDate(rental.rentDate) }}</td>
          <td>
            {{
              rental.returnDate ? formatDate(rental.returnDate) : "Nie zwrócono"
            }}
          </td>
          <td>
            <button @click="editRental(rental)">Edytuj</button>
            <button @click="removeRental(rental.id)">Usuń</button>
          </td>
        </tr>
      </tbody>
    </table>

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
  getAllRentals,
  addRental,
  updateRental,
  deleteRental,
} from "../services/rentals";
import { getAllReaders } from "../services/readers";
import { getAllBooks } from "../services/books";

const rentals = ref([]);
const readers = ref([]);
const books = ref([]);
const error = ref(null);

const page = ref(0);
const totalPages = ref(1);
const pageSize = 5;

const form = ref({
  readerId: "",
  bookId: "",
  rentDate: new Date().toISOString().split("T")[0],
  returnDate: "",
});
const editing = ref(false);
const editingId = ref(null);

const formatDate = (dateString) => {
  if (!dateString) return "";
  const options = { year: "numeric", month: "2-digit", day: "2-digit" };
  return new Date(dateString).toLocaleDateString("pl-PL", options);
};

//merge all pages together - ok solution for the amount of data we have
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

const fetchReaders = async () => {
  await fetchAllPages(getAllReaders, readers);
};

const fetchBooks = async () => {
  await fetchAllPages(getAllBooks, books);
};

const fetchRentals = async () => {
  try {
    const res = await getAllRentals(page.value, pageSize);
    rentals.value = res.data.content;
    totalPages.value = res.data.totalPages;
    error.value = null;
  } catch (err) {
    error.value = "Wystąpił błąd podczas ładowania wypożyczeń";
    console.error(err);
  }
};

const handleSaveRental = async () => {
  try {
    if (!form.value.readerId || !form.value.bookId || !form.value.rentDate) {
      error.value = "Proszę wypełnić wymagane pola";
      return;
    }

    const rentalData = {
      readerId: form.value.readerId,
      bookId: form.value.bookId,
      rentDate: form.value.rentDate,
      returnDate: form.value.returnDate || null,
    };

    if (editing.value) {
      await updateRental(editingId.value, rentalData);
    } else {
      await addRental(rentalData);
    }

    resetForm();
    await fetchRentals();
    error.value = null;
  } catch (err) {
    error.value =
      err.response?.data?.message ||
      "Wystąpił błąd podczas zapisywania wypożyczenia";
    console.error(err);
  }
};

const editRental = (rental) => {
  form.value.readerId = rental.reader?.id || "";
  form.value.bookId = rental.book?.id || "";
  form.value.rentDate = rental.rentDate ? rental.rentDate.split("T")[0] : "";
  form.value.returnDate = rental.returnDate
    ? rental.returnDate.split("T")[0]
    : "";
  editing.value = true;
  editingId.value = rental.id;
};

const removeRental = async (id) => {
  try {
    if (confirm("Czy na pewno chcesz usunąć to wypożyczenie?")) {
      await deleteRental(id);

      // cofnięcie strony jeśli ostatni element został usunięty
      if (rentals.value.length === 1 && page.value > 0) {
        page.value--;
      }

      await fetchRentals();
      error.value = null;
    }
  } catch (err) {
    error.value = "Wystąpił błąd podczas usuwania wypożyczenia";
    console.error(err);
  }
};

const resetForm = () => {
  form.value = {
    readerId: "",
    bookId: "",
    rentDate: new Date().toISOString().split("T")[0],
    returnDate: "",
  };
  editing.value = false;
  editingId.value = null;
};

const nextPage = () => {
  if (page.value + 1 < totalPages.value) {
    page.value++;
    fetchRentals();
  }
};

const prevPage = () => {
  if (page.value > 0) {
    page.value--;
    fetchRentals();
  }
};

onMounted(() => {
  fetchRentals();
  fetchReaders();
  fetchBooks();
});
</script>

<style scoped>
.date-row {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  max-width: 50%;
  margin: 0 auto;
}

.date-field {
  display: flex;
  flex-direction: column;
  flex: 1;
  min-width: 150px;
}

.date-field input[type="date"] {
  max-width: 10rem;
  margin: 0 auto;
  width: 100%;
  width: 80%;
}
.error-message {
  color: red;
  margin: 10px 0;
}

.rentals-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  border: 1px solid white;
}

.rentals-table thead {
  background-color: #525457;
  color: #ffffff;
  font-weight: bold;
}

.rentals-table th,
.rentals-table td {
  border: 0px solid #ddd;
  padding: 8px;
  text-align: left;
  padding: 5px 30px;
  text-align: center;
}

table tbody tr:nth-child(odd) {
  background-color: #313131;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

select,
input[type="date"] {
  padding: 8px;
  margin-right: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

input[type="date"] {
  width: 200px;
}

button {
  margin-left: 5px;
  cursor: pointer;
}

.form-actions {
  margin-top: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}
</style>