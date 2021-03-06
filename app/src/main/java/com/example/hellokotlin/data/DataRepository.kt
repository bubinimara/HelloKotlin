package com.example.hellokotlin.data

import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun configuration():Resource<ConfigurationResponse>
    suspend fun login(username: String, password: String): Resource<User>
    suspend fun getMovies(): Flow<Resource<List<Movie>>>
    suspend fun getPopularUsers(): Resource<List<User>>
    suspend fun loadLastSession(): Resource<User>
    suspend fun logout(): Resource<Boolean>
    suspend fun getMovieById(id:Int): Flow<Resource<Movie>>
    suspend fun rateMovie(movie: Movie, rate:Int): Flow<Resource<Boolean>>
    suspend fun addMovie(movie: Movie)
}