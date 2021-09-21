package com.example.hellokotlin.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
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
    suspend fun inster(accountState: Movie.AccountState)

    @Query("delete from account_state")
    suspend fun deleteAllAccountState()

    @Query("select * from account_state where id = :id")
    suspend fun getAccountState(id: Int): Movie.AccountState?
}