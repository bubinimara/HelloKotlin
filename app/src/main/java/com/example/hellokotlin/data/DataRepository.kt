package com.example.hellokotlin.data

import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun configuration():Flow<Resource<ConfigurationResponse>> // todo:this could be extenral repo??
    suspend fun loadLastSession(): Flow<Resource<User>>
    suspend fun login(username: String, password: String): Flow<Resource<User>>
    suspend fun logout(): Flow<Resource<Boolean>>
    suspend fun getMovies(): Flow<Resource<List<Movie>>>
    suspend fun getMovieById(id:Int): Flow<Resource<Movie>>
    suspend fun rateMovie(movie: Movie, rate:Int): Flow<Resource<Boolean>>
    suspend fun getPopularUsers(): Flow<Resource<List<User>>>// todo:to remove
}