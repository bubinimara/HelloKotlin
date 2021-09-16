package com.example.hellokotlin.data

import android.util.Log
import com.example.hellokotlin.data.local.SessionManager
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.ApiService
import com.example.hellokotlin.data.network.NetworkServices
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import com.example.hellokotlin.data.network.model.LoginRequest
import kotlinx.coroutines.*

/**
 *
 * Created by Davide Parise on 30/08/21.
 */
class DataRepositoryImpl constructor(private val apiService:ApiService,
                            private val sessionManager: SessionManager) : DataRepository {

    override suspend fun configuration(): Resource<ConfigurationResponse> {
        return withContext(Dispatchers.IO){
            try {
                Resource.Success(apiService.configuration())
            } catch (e: Exception) {
                Resource.Error()
            }
        }
    }

    override suspend fun login(username:String, password:String):Resource<User> {
         return withContext(Dispatchers.IO) {
             if(sessionManager.loadSession().isEmpty()) {
                 val requestToken: String = apiService.requestToken().request_token
                 val token = apiService.login(LoginRequest(username,password,requestToken))
                 val sessionIdResponse = apiService.createSessionId(token.request_token)
                 if(sessionIdResponse.sessionId.isEmpty()) {
                     Resource.Error(Resource.Error.ErrorCode.INVALID_TOKEN)
                 }else {
                     sessionManager.storeSession(sessionIdResponse.sessionId)
                     Resource.Success(User(name = username))
                 }
             }else{
                 Resource.Success(User(name = username))
             }
         }

    }

    override suspend fun getMovies():Resource<List<Movie>>{
        return withContext(Dispatchers.IO){
            try {
                val response = apiService.popularMovies()
                Resource.Success(response.results)
            } catch (e: Exception) {
                Resource.Error()
            }
        }
    }
    override suspend fun getPopularUsers():Resource<List<User>>{
        return withContext(Dispatchers.IO){
            try {
                val items = apiService.popularUsers().results
                Resource.Success(items)
            }catch (e:Exception){
                Resource.Error()
            }
        }
    }



}