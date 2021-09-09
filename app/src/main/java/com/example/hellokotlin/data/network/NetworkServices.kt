package com.example.hellokotlin.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL


/**
 *
 * Created by Davide Parise on 08/09/21.
 */
private const val contentType = "Content-Type"
private const val contentTypeValue = "application/json;charset=utf-8"

class NetworkServices {
    private companion object {
        val BASE_URL = "https://api.themoviedb.org/"
        val API_KEY = "8ced8d8747325611d6611a0dc8259b02"
    }


    private var interceptor = Interceptor { chain ->
        val original = chain.request()

        val url = original.url.newBuilder()
            .addEncodedQueryParameter("api_key", API_KEY)
            .build()

        val request = original.newBuilder()
            .url(url)
            .header(contentType, contentTypeValue)
            .build()

        chain.proceed(request)
    }


    private val retrofit:Retrofit
    val apiService:ApiService

    init {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(logInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            //.addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

}