package com.example.hellokotlin.data

import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User

interface DataRepository {
    suspend fun login(username: String, password: String): Resource<User>
    fun getMovies(): Resource<List<Movie>>
    fun getLastActiveUsers(): Resource<List<User>>
}