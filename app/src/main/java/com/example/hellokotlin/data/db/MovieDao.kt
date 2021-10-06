package com.example.hellokotlin.data.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.hellokotlin.data.model.Movie
import kotlinx.coroutines.flow.Flow
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
    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(movies: List<Movie>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("delete from movies")
    suspend fun deleteAllMovies()

    @Query("select * from movies where id=:id" )
    fun getMoviesByIdAsFlow(id: Int): Flow<Movie?>

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


}