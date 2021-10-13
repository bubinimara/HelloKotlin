package com.example.hellokotlin.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.example.hellokotlin.data.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 *
 * Created by Davide Parise on 21/09/21.
 */
@Dao
 interface MovieDao {

    /*******************************************************************/
    /*********************** ACCOUNT STATE *****************************/
    /*******************************************************************/

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


    /*******************************************************************/
    /************************** MOVIES *********************************/
    /*******************************************************************/


    @Query("select * from movies")
    fun getMoviesAsFlow():Flow<List<Movie>?>

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

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(movies: List<Movie>)

    @Insert(onConflict = REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("delete from movies")
    suspend fun deleteAllMovies()


    /*******************************************************************/
    /**************************** ALL **********************************/
    /*******************************************************************/

    @Transaction
    suspend fun getMovieAndAccountStateByIdAsFlow(id: Int): Flow<Movie?>{
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

    @Transaction
    suspend fun updateMovieAndAccountState(movie: Movie){
        insertMovie(movie)
        movie.accountState?.let { insertAccountState(it) }
    }
}