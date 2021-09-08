package com.example.hellokotlin.data

import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User

/**
 *
 * Created by Davide Parise on 30/08/21.
 */
class DataRepositoryImpl : DataRepository {
    override fun login(username:String, password:String):Resource<User> {
        if (username.isEmpty()) // some checks
        //simulate error if not passed
            return Resource.Error(400)

        return Resource.Success(User(username))
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