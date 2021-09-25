package com.example.hellokotlin.data

import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.model.ConfigurationResponse

interface DataRepository {
    suspend fun configuration():Resource<ConfigurationResponse>
    suspend fun login(username: String, password: String): Resource<User>
    suspend fun getMovies(): Resource<List<Movie>>
    suspend fun getPopularUsers(): Resource<List<User>>
    suspend fun loadLastSession(): Resource<User>
    suspend fun logout(): Resource<Boolean>
}