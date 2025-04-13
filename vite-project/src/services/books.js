import axios from 'axios'

const API_URL = 'http://localhost:8080/books'

export const getAllBooks = (page = 0, size = 5) =>
    axios.get(`${API_URL}?page=${page}&size=${size}`)
export const getBook = (id) => axios.get(`${API_URL}/${id}`)
export const saveBook = (book) => axios.post(API_URL, book)
export const updateBook = (id, book) => axios.put(`${API_URL}/${id}`, book)
export const deleteBook = (id) => axios.delete(`${API_URL}/${id}`)
