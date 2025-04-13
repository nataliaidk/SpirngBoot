import axios from 'axios'

const API_URL = 'http://localhost:8080/readers'

export const getAllReaders = (page = 0, size = 5) =>
    axios.get(`${API_URL}?page=${page}&size=${size}`)
export const getReader = (id) => axios.get(`${API_URL}/${id}`)
export const addReader = (name) => axios.post(API_URL, { name })
export const updateReader = (id, reader) => axios.put(`${API_URL}/${id}`, reader)
export const deleteReader = (id) => axios.delete(`${API_URL}/${id}`)