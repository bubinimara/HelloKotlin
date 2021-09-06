package com.example.hellokotlin.data

import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User


/**
 *
 * Created by Davide Parise on 30/08/21.
 */
class DataRepository {
    public fun login(username:String,password:String):Resource<User>{
        if(username.isEmpty()) // some checks
            //simulate error if not passed
            return Resource.Error(400)

        if(username.equals(password))
            return Resource.Success(User(username))
        return Resource.Success(null)
    }

    fun getMovies():Resource<List<Movie>>{
        return Resource.Success(emptyList())
    }
    fun getLastActiveUsers():Resource<List<User>>{
        return Resource.Success(null)
    }
}