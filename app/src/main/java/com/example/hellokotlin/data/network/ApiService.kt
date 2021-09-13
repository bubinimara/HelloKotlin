package com.example.hellokotlin.data.network

import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import com.example.hellokotlin.data.network.model.ListResponse
import com.example.hellokotlin.data.network.model.LoginRequest
import com.example.hellokotlin.data.network.model.Token
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 *
 * Created by Davide Parise on 09/09/21.
 */
interface ApiService {
    @GET("3/authentication/token/new")
    suspend fun requestToken():Token

    @POST("3/authentication/token/validate_with_login")
    suspend fun login(@Body request:LoginRequest):Token

    @GET("3/person/popular")
    suspend fun popularUsers(@Query("page")page:Int = 1): ListResponse<User>

    @GET("3/movie/popular")
    suspend fun popularMovies(@Query("page")page: Int = 1):ListResponse<Movie>

    @GET("3/configuration")
    suspend fun configuration(): ConfigurationResponse
}