import axios from 'axios'

const BASE = 'http://localhost:8080/swagger'  // dopasuj do swojego backendu

export default {
  getBooks(page = 0) {
    return axios.get(`${BASE}/books?page=${page}&size=5`)
  },
  createBook(book) {
    return axios.post(`${BASE}/books`, book)
  },
  deleteBook(id) {
    return axios.delete(`${BASE}/books/${id}`)
  }
}
