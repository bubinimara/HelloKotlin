package com.example.hellokotlin.data

import android.util.Log
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.network.NetworkServices
import com.example.hellokotlin.data.network.model.LoginRequest
import kotlinx.coroutines.*

/**
 *
 * Created by Davide Parise on 30/08/21.
 */
class DataRepositoryImpl : DataRepository {
    companion object {
        private const val TAG = "DataRepositoryImpl"
    }

    private val apiService = NetworkServices().apiService

    override suspend fun login(username:String, password:String):Resource<User> {
        Log.d(TAG, "login: $username $password")
         return withContext(Dispatchers.IO) {
             val requestToken: String = apiService.requestToken().request_token
             val token = apiService.login(LoginRequest(username,password,requestToken))
             if(token.request_token.isEmpty()){
                 Resource.Error(Resource.Error.ErrorCode.INVALID_TOKEN)
             }else {
                 Resource.Success(User(username))
             }

         }
    }

    override fun getMovies():Resource<List<Movie>>{
        val movies:MutableList<Movie> = ArrayList()
        for (i:Int in 1..10){
            movies.add(Movie("Movie $i"))
        }
        return Resource.Success(movies)
    }
    override fun getLastActiveUsers():Resource<List<User>>{
        val users:MutableList<User> = ArrayList<User>()
        for(i:Int in  1..10){
                users.add(User("User $i"))
        }
        return Resource.Success(users)
    }

}