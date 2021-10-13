package com.example.hellokotlin.data.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.hellokotlin.data.model.Movie
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select


/**
 *
 * Created by Davide Parise on 21/09/21.
 */
@Dao
 interface MovieDao {
    @Query("select * from account_state ")
    suspend fun getAllAccountState():List<Movie.AccountState>

    @Transaction
    suspend fun insertAllAccountState(movies: List<Movie>){
        movies.forEach { it ->
            it.accountState?.apply {
                insertAccountState(this)
            }
        }
    }

    @Insert(onConflict = REPLACE)
    suspend fun insertAccountState(accountState: Movie.AccountState)

    @Query("delete from account_state")
    suspend fun deleteAllAccountState()

    @Query("select * from account_state where id = :id")
    suspend fun getAccountState(id: Int): Movie.AccountState?


    @Query("select * from movies")
    suspend fun getMovies(): List<Movie>?

    @Transaction
    suspend fun getMoviesAndAccountState(): List<Movie>? {
        val  movies = getMovies()
        movies?.forEach {
            it.accountState = getAccountState(it.id)
        }
        return movies
    }

    @Query("select * from movies")
    fun getMoviesAsFlow():Flow<List<Movie>?>

    @Query("select * from movies where id=:id" )
    fun getMoviesByIdAsFlow(id: Int): Flow<Movie?>

    @Transaction
    fun getMovieAndAccountStateByIdAsFlow(id: Int): Flow<Movie?>{
        return getMoviesByIdAsFlow(id).map {
            it?.apply {
                accountState = getAccountState(id)
            }
        }
    }

    @Transaction
    suspend fun getMoviesAndAccountStateAsFlow(): Flow<List<Movie>?>{
        return getMoviesAsFlow().map {
            it?.forEach { movie ->
                movie.accountState = getAccountState(movie.id)
            }
            it
        }
    }



    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(movies: List<Movie>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("delete from movies")
    suspend fun deleteAllMovies()


    @Query("select * from movies where id=:id" )
    suspend fun getMoviesById(id: Int): Movie?

    @Transaction
    suspend fun updateAllMovies(movies: List<Movie>){
        deleteAllAccountState()
        deleteAllMovies()
        insertAllMovies(movies)
        insertAllAccountState(movies)

    }

    @Transaction
    suspend fun updateMovieAndAccountState(movie: Movie){
        insertMovie(movie)
        movie.accountState?.let { insertAccountState(it) }
    }

    @Insert(onConflict = REPLACE)
    suspend fun addMovie(movie: Movie)



}