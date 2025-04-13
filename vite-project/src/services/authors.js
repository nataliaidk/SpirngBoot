import axios from 'axios'
const API_URL = 'http://localhost:8080/authors'
 // zamiast localhost:8080

export const getAllAuthors = () => axios.get(API_URL)

export const getAuthorById = (id) => axios.get(`${API_URL}/${id}`)

export const addAuthor = (author) => axios.post(API_URL, author)

export const updateAuthor = (id, author) => axios.put(`${API_URL}/${id}`, author)

export const deleteAuthor = (id) => axios.delete(`${API_URL}/${id}`)
