package com.example.hellokotlin.data.network

import com.example.hellokotlin.data.network.model.LoginRequest
import com.example.hellokotlin.data.network.model.Token
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


/**
 *
 * Created by Davide Parise on 09/09/21.
 */
interface ApiService {
    @GET("3/authentication/token/new")
    suspend fun requestToken():Token

    @POST("3/authentication/token/validate_with_login")
    suspend fun login(@Body request:LoginRequest):Token
}