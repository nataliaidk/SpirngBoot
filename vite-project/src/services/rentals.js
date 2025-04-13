import axios from 'axios'

const API_URL = 'http://localhost:8080/rentals'

export const getAllRentals = (page = 0, size = 5) =>
    axios.get(`${API_URL}?page=${page}&size=${size}`)
export const getRental = (id) => axios.get(`${API_URL}/${id}`)
export const addRental = (rentalData) => axios.post(API_URL, rentalData)
export const updateRental = (id, rentalData) => axios.put(`${API_URL}/${id}`, rentalData)
export const deleteRental = (id) => axios.delete(`${API_URL}/${id}`)