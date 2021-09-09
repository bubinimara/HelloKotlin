package com.example.hellokotlin.data.network.model


/**
 *
 * Created by Davide Parise on 09/09/21.
 */
data class LoginRequest (
    val username:String,
    val password:String,
    val request_token:String)