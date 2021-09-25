package com.example.hellokotlin.data.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.hellokotlin.data.model.Movie
import kotlinx.coroutines.selects.select


/**
 *
 * Created by Davide Parise on 21/09/21.
 */
@Dao
interface MovieDao {
    @Query("select * from account_state ")
    suspend fun getAllAccountState():List<Movie.AccountState>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllAccountState(accountState: List<Movie.AccountState>)

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

    @Query("delete from movies")
    suspend fun deleteAllMovies()


}