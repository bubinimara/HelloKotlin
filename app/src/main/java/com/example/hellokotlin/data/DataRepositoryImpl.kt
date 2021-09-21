package com.example.hellokotlin.data

import android.util.Log
import com.example.hellokotlin.data.local.Session
import com.example.hellokotlin.data.local.SessionManager
import com.example.hellokotlin.data.local.SessionManagerImpl
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.ApiService
import com.example.hellokotlin.data.network.model.ConfigurationResponse
import com.example.hellokotlin.data.network.model.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *
 * Created by Davide Parise on 30/08/21.
 */
class DataRepositoryImpl constructor(private val apiService:ApiService,
                            private val sessionManager: SessionManager
) : DataRepository {

    override suspend fun configuration(): Resource<ConfigurationResponse> {
        return withContext(Dispatchers.IO){
            try {
                Resource.Success(apiService.configuration())
            } catch (e: Exception) {
                Resource.Error()
            }
        }
    }

     override suspend fun loadLastSession():Resource<User>{
        val session = sessionManager.loadSession()
        var user:User ?= null
        if(session.isValid()){
            user = User(name = session.username)
        }
        return Resource.Success(user)
    }

    override suspend fun logout(): Resource<Boolean> {
        sessionManager.clear()
        return Resource.Success(true)
    }

    override suspend fun login(username:String, password:String):Resource<User> {
         return withContext(Dispatchers.IO) {
             val requestToken: String = apiService.requestToken().request_token
             val tokenResponse = apiService.login(LoginRequest(username,password,requestToken))
             val sessionIdResponse = apiService.createSessionId(tokenResponse)
             if(!sessionIdResponse.success || sessionIdResponse.sessionId == null) {
                 Resource.Error(Resource.Error.ErrorCode.INVALID_TOKEN)
             }else {
                 val s = Session(username, sessionIdResponse.sessionId)
                 sessionManager.storeSession(s)
                 Resource.Success(User(name = username))
             }
         }

    }

    override suspend fun getMovies():Resource<List<Movie>>{
        return withContext(Dispatchers.IO){
            try {
                val response = apiService.popularMovies()
                val list = response.results?.toMutableList()
                list.forEach {
                    it.accountState = apiService.accountState(it.id,sessionManager.loadSession().sessionId)
                }
                Resource.Success(list)
            } catch (e: Exception) {
                Log.e("DataRepository", "getPopularUsers: ",e )
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