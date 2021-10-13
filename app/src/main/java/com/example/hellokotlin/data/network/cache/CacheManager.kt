package com.example.hellokotlin.data.network.cache

import android.content.Context
import android.content.SharedPreferences
import com.example.hellokotlin.data.db.AppDb
import com.example.hellokotlin.data.db.MovieDao
import com.example.hellokotlin.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit


/**
 *
 * Created by Davide Parise on 22/09/21.
 *
 * Add local var to store value from db
 */
class CacheManager private constructor(context: Context,appDb: AppDb) {
    val movieDao:MovieDao
    val cacheValidator:CacheValidator
    init {
        movieDao = appDb.movieDao()
        cacheValidator = CacheValidator(context)
    }

    companion object{

        private var INSTANCE:CacheManager ?= null

        fun getInstance(context: Context,appDb: AppDb):CacheManager{
            return INSTANCE ?: synchronized(this){
                val instance = CacheManager(context,appDb)
                INSTANCE = instance
                // return
                instance
            }
        }
    }

    suspend fun getMovies(force: Boolean = false): List<Movie>? {
        if(force || cacheValidator.isValidMovieCache() ){
            return movieDao.getMoviesAndAccountState()
        }

        return null
    }

     suspend fun getMoviesAsFlow(force: Boolean = false): Flow<List<Movie>?> {
        return movieDao.getMoviesAndAccountStateAsFlow()
    }

    suspend fun updateMovies(movies:List<Movie>){
        // clear all
        movieDao.updateAllMovies(movies)
        cacheValidator.updateMovieCache()
    }

    suspend fun getMovieByIdAsFlow(id: Int): Flow<Movie?> {
        return movieDao.getMovieAndAccountStateByIdAsFlow(id)
    }
    suspend fun getMovieById(id: Int): Movie? {
        return movieDao.getMoviesById(id)?.apply {
            accountState = movieDao.getAccountState(id)
        }
    }

    suspend fun updateMovieAndAccountState(movie: Movie) {
        movieDao.updateMovieAndAccountState(movie)
    }
    suspend fun getAccountState(movie: Movie): Movie.AccountState? {
        return movieDao.getAccountState(movie.id)
    }

    suspend fun addMovie(movie: Movie) {
        movieDao.addMovie(movie)
    }

    class CacheValidator(context: Context){
        val sharedPref:SharedPreferences
        init {
            sharedPref = context.applicationContext.getSharedPreferences("com.example.hellokotlin.data.network.cache"+".MovieCache",Context.MODE_PRIVATE)
        }

        companion object{
            private val TIME_TO_LIVE = TimeUnit.DAYS.toMillis(1)
            private val KEY_MOVIE_TIMESTAMP = "movie_timestamp"
        }

        fun isValidMovieCache():Boolean {
            val lastAccess = sharedPref.getLong(KEY_MOVIE_TIMESTAMP, 0)
            return (System.currentTimeMillis() - lastAccess) < TIME_TO_LIVE
        }

        fun updateMovieCache() {
            sharedPref.edit()
                .putLong(KEY_MOVIE_TIMESTAMP,System.currentTimeMillis())
                .apply()
        }

    }
}